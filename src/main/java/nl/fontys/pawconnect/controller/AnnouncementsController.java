package nl.fontys.pawconnect.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.interf.announcements.CreateAnnouncementUseCase;
import nl.fontys.pawconnect.domain.requests.announcements.CreateAnnouncementRequest;
import nl.fontys.pawconnect.domain.responses.announcements.CreateAnnouncementResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/announce")
@AllArgsConstructor
//@CrossOrigin(origins = {"http://127.0.0.1:5173/"})
public class AnnouncementsController {
    private final CreateAnnouncementUseCase createAnnouncementUseCase;

    @PostMapping("")
    public ResponseEntity<CreateAnnouncementResponse> createAnnouncement(@RequestBody @Valid CreateAnnouncementRequest request) {
        CreateAnnouncementResponse response = createAnnouncementUseCase.createAnnouncement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
