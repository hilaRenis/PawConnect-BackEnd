package nl.fontys.pawconnect.business.impl.users;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.EmailAlreadyExistsException;
import nl.fontys.pawconnect.business.exception.InvalidPasswordException;
import nl.fontys.pawconnect.business.exception.UsernameAlreadyExistsException;
import nl.fontys.pawconnect.business.impl.validation.PasswordValidator;
import nl.fontys.pawconnect.business.interf.users.CreateUserUseCase;
import nl.fontys.pawconnect.configuration.security.token.AccessTokenEncoder;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.users.CreateUserRequest;
import nl.fontys.pawconnect.domain.responses.users.CreateUserResponse;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.entity.UserRoles;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    private final PasswordValidator passwordValidator;

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        String errors = passwordValidator.validatePassword(request.getPassword());
        if(!errors.isEmpty()) {
            throw new InvalidPasswordException(errors);
        }

        UserEntity savedUser = saveNewUser(request);
        String accessToken = generateAccessToken(savedUser);
        return CreateUserResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private UserEntity saveNewUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity newUser = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .fullName(request.getFullName().trim())
                .role(UserRoles.USER_NORMAL)
                .build();
        return userRepository.save(newUser);
    }

    private String generateAccessToken(UserEntity user) {
        String userId = user.getId() != null ? user.getId() : null;
        return accessTokenEncoder.encode(
                new AccessToken(user.getUsername(), userId, user.getRole()));
    }
}
