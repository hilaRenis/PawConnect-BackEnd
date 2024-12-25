package nl.fontys.pawconnect.persistence.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "images")
@EqualsAndHashCode
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id;

    @NotNull
    @Column(name = "url")
    private String url;
}
