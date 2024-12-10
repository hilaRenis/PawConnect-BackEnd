package nl.fontys.pawconnect.domain;


import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;

    @NotNull
    private User sender;

    @NotNull
    private User recipient;

    @NotNull
    private String content;

    @NotNull
    private Date dateSent;
}
