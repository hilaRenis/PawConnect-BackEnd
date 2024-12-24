package nl.fontys.pawconnect.persistence.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
@Entity
@Table(name = "messages")
@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @NotNull
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    @NotNull
    private UserEntity recipient;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "date_sent")
    @NotNull
    private Date dateSent;

    @Nullable
    @Column(name = "reference_post_id")
    private String referencedPostUUID;
}
