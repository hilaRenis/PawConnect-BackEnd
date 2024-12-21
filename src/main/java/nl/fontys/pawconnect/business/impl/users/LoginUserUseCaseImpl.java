package nl.fontys.pawconnect.business.impl.users;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.LoginFailedException;
import nl.fontys.pawconnect.business.interf.users.LoginUserUseCase;
import nl.fontys.pawconnect.configuration.security.token.AccessTokenEncoder;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.users.LoginUserRequest;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenEncoder accessTokenEncoder;

    @Override
    @Transactional
    public String loginUser(LoginUserRequest request){
        Optional<UserEntity> loginUser = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
        if(loginUser.isEmpty()){
            throw new LoginFailedException();
        }
        if(!matchesPassword(request.getPassword(), loginUser.get().getPassword())) {
            throw new LoginFailedException();
        }

        return generateAccessToken(loginUser.get());
    }

    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private String generateAccessToken(UserEntity user) {
        String userId = user.getId() != null ? user.getId() : null;
        return accessTokenEncoder.encode(
                new AccessToken(user.getUsername(), userId, user.getRole()));
    }
}
