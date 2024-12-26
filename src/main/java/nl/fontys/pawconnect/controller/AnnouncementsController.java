package nl.fontys.pawconnect.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.impl.validation.DeserializationValidator;
import nl.fontys.pawconnect.business.interf.announcements.CreateAnnouncementUseCase;
import nl.fontys.pawconnect.business.interf.announcements.GetAnnouncementUseCase;
import nl.fontys.pawconnect.business.interf.announcements.GetVisibleAnnouncementsUseCase;
import nl.fontys.pawconnect.business.interf.announcements.UpdateAnnouncementUseCase;
import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.requests.announcements.GetAnnouncementRequest;
import nl.fontys.pawconnect.domain.requests.announcements.GetVisibleAnnouncementsRequest;
import nl.fontys.pawconnect.domain.requests.announcements.UpdateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import nl.fontys.pawconnect.domain.responses.announcements.GetAnnouncementResponse;
import nl.fontys.pawconnect.domain.responses.announcements.GetVisibleAnnouncementsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CreateAnnouncementUseCase createAnnouncementUseCase;
    private final GetVisibleAnnouncementsUseCase getVisibleAnnouncementsUseCase;
    private final GetAnnouncementUseCase getAnnouncementUseCase;
    private final UpdateAnnouncementUseCase updateAnnouncementUseCase;

    private static final Logger LOG = LoggerFactory.getLogger(AnnouncementsController.class);

    @PostMapping("")
    public ResponseEntity<String> createAnnouncement(@RequestParam("images") List<MultipartFile> images,
                                                     @RequestParam("announcement") String announcementJson) {
        try{
            CreateAnnouncementRequest request = DeserializationValidator.validateAndDeserialize(announcementJson, CreateAnnouncementRequest.class);
            CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(images, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response.getAnnouncementUUID());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process the request: " + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<GetVisibleAnnouncementsResponse> getVisibleAnnouncements(@RequestBody @Valid GetVisibleAnnouncementsRequest request) {
        GetVisibleAnnouncementsResponse response = getVisibleAnnouncementsUseCase.getVisibleAnnouncements(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetAnnouncementResponse> getAnnouncement(@PathVariable(value = "id") final String announcementId,
                                                                   @RequestBody @Valid GetAnnouncementRequest request) {
        GetAnnouncementResponse response = getAnnouncementUseCase.getAnnouncement(request, announcementId);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAnnouncement(@PathVariable(value = "id") final String id,
                                                   @RequestParam(value = "data", required = false) String requestJson,
                                                   @RequestParam(value = "imagesToAdd", required = false) List<MultipartFile> images) {
        try {
            LOG.info("Trying to deserialize: {}", requestJson);
            UpdateAnnouncementRequest request = DeserializationValidator.validateAndDeserialize(requestJson, UpdateAnnouncementRequest.class);
            updateAnnouncementUseCase.updateAnnouncement(id, request, images);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
