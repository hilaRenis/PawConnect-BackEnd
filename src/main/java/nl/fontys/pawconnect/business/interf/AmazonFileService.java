package nl.fontys.pawconnect.business.interf;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonFileService {
    String uploadFile(MultipartFile multipartFile, String fileName);
}
