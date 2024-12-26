package nl.fontys.pawconnect.business.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nl.fontys.pawconnect.business.exception.FileUploadException;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import nl.fontys.pawconnect.persistence.entity.ImageEntity;
import nl.fontys.pawconnect.persistence.interf.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmazonFileServiceImpl implements AmazonFileService {

    private static final Logger LOG = LoggerFactory.getLogger(AmazonFileServiceImpl.class);

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String s3BucketRegion;

    @Value("${aws.s3.bucket.link}")
    private String s3BucketLink;

    private AmazonS3 s3Client;

    private final ImageRepository imageRepository;

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(s3BucketRegion)
                .build();
    }

    @Override
    @Transactional
    public ImageEntity uploadFile(MultipartFile multipartFile, String directoryPath) {
        try {
            // Uploading to S3 with generated UUID requires saving first with empty URL
            ImageEntity image = ImageEntity.builder()
                    .url("")
                    .build();
            image = imageRepository.save(image);  // Now image has the generated ID

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            //Extracts file extension
            String fileExtension = (multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().contains("."))
                    ? multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."))
                    : "";
            String bucketFilePath = directoryPath + image.getId() + fileExtension;
            s3Client.putObject(s3BucketName, bucketFilePath, multipartFile.getInputStream(), objectMetadata);
            image.setUrl(String.format("%s/%s", s3BucketLink, bucketFilePath));

            return imageRepository.save(image);

        } catch (IOException e) {
            LOG.error("Error occurred ==> {}", e.getMessage());
            throw new FileUploadException("Error occurred in file upload ==> "+e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteFile(String fileKey) {
        try {
            s3Client.deleteObject(s3BucketName, fileKey);
            LOG.info("File successfully deleted from S3: {}", fileKey);

            Optional<ImageEntity> imageOptional = imageRepository.findByUrl(String.format("%s/%s", s3BucketLink, fileKey));
            if (imageOptional.isPresent()) {
                imageRepository.delete(imageOptional.get());
            } else {
                LOG.warn("No ImageEntity found for fileKey: {}", fileKey);
            }
        } catch (Exception e) {
            LOG.error("Error occurred while deleting file: {}", e.getMessage());
            throw new RuntimeException("Error occurred while deleting file: " + e.getMessage());
        }
    }
}