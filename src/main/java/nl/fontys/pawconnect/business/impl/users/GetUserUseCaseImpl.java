package nl.fontys.pawconnect.business.impl.users;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.impl.converter.UserConverter;
import nl.fontys.pawconnect.business.exception.InvalidUserException;
import nl.fontys.pawconnect.business.impl.validation.LoginValidator;
import nl.fontys.pawconnect.business.interf.users.GetUserUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.User;
import nl.fontys.pawconnect.domain.requests.users.GetUserRequest;
import nl.fontys.pawconnect.domain.responses.users.GetUserResponse;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {
    private UserRepository userRepository;
    private LoginValidator loginValidator;
    private AccessToken accessToken;

    @Override
    @Transactional
    public GetUserResponse getUser(GetUserRequest request) {
        loginValidator.validateToken(accessToken, request.getUserId());

        Optional<User> userOptional = userRepository.findById(request.getUserId())
                .map(UserConverter::convert);

        if(userOptional.isEmpty()){
            throw new InvalidUserException("INVALID_USER_ID");
        } else {
            return GetUserResponse.builder()
                    .id(userOptional.get().getId())
                    .fullName(userOptional.get().getFullName())
                    .email(userOptional.get().getEmail())
                    .username(userOptional.get().getUsername())
                    .role(userOptional.get().getRole())
                .build();
        }
    }
}
