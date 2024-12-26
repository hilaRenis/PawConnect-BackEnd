package nl.fontys.pawconnect.business.interf;

import nl.fontys.pawconnect.persistence.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonFileService {
    ImageEntity uploadFile(MultipartFile multipartFile, String fileName);

    void deleteFile(String fileKey);
}
