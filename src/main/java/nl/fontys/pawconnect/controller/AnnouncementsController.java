package nl.fontys.pawconnect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.interf.announcements.CreateAnnouncementUseCase;
import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@AllArgsConstructor
//@CrossOrigin(origins = {"http://127.0.0.1:5173/"})
public class AnnouncementsController {
    private final ObjectMapper objectMapper;

    private final CreateAnnouncementUseCase createAnnouncementUseCase;

    @PostMapping("")
    public ResponseEntity<String> createAnnouncement(@RequestParam("images") List<MultipartFile> images,
                                                     @RequestParam("announcement") String announcementJson) {
        try{
            CreateAnnouncementRequest request = objectMapper.readValue(announcementJson, CreateAnnouncementRequest.class);
            CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(images, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getAnnouncementUUID());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process the request: " + e.getMessage());
        }
    }


}
