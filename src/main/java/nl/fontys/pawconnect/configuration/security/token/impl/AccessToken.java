package nl.fontys.pawconnect.configuration.security.token.impl;

import lombok.*;
import nl.fontys.pawconnect.persistence.entity.UserRoles;

@EqualsAndHashCode
@Data
@Builder
@AllArgsConstructor
public class AccessToken {
    private final String subject;
    private final Long userId;
    private final UserRoles role;

}
