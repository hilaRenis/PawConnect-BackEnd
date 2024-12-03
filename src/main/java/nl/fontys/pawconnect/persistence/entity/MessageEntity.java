package nl.fontys.pawconnect.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Date;
import java.util.UUID;

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
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @NotNull
    private UserEntity sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    @NotNull
    private UserEntity recipient;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "date_sent")
    @NotNull
    private Date dateSent;
}
