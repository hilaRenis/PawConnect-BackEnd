package nl.fontys.pawconnect.domain;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private String id;
    private String title;
    private String description;
    private Date dateMade;
    private User announcer;
}
