package nl.fontys.pawconnect.domain;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private String id;
    private String title;
    private String description;
    private Date dateMade;
    private List<Image> images;
    private UserDTO announcer;
}
