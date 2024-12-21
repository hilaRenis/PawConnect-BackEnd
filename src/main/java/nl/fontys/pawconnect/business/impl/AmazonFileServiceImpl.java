package nl.fontys.pawconnect.business.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.annotation.PostConstruct;
import nl.fontys.pawconnect.business.exception.FileUploadException;
import nl.fontys.pawconnect.business.interf.AmazonFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
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

    @PostConstruct
    private void initialize() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(s3BucketRegion)
                .build();
    }

//    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
//        final File file = new File(multipartFile.getOriginalFilename());
//        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
//            outputStream.write(multipartFile.getBytes());
//        } catch (IOException e) {
//            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
//        }
//        return file;
//    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String filePath) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            //Extracts file extension
            String fileExtension = (multipartFile.getOriginalFilename() != null && multipartFile.getOriginalFilename().contains("."))
                    ? multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."))
                    : "";
            String bucketFilePath = filePath + fileExtension;
            s3Client.putObject(s3BucketName, bucketFilePath, multipartFile.getInputStream(), objectMetadata);
            return String.format("%s/%s", s3BucketLink, bucketFilePath);

        } catch (IOException e) {
            LOG.error("Error occurred ==> {}", e.getMessage());
            throw new FileUploadException("Error occurred in file upload ==> "+e.getMessage());
        }
    }
}