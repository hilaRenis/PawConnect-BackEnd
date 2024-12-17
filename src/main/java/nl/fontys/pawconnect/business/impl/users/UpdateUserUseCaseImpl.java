package nl.fontys.pawconnect.business.impl.users;

import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.exception.*;
import nl.fontys.pawconnect.business.interf.users.UpdateUserUseCase;
import nl.fontys.pawconnect.configuration.security.token.impl.AccessToken;
import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;
import nl.fontys.pawconnect.persistence.entity.UserEntity;
import nl.fontys.pawconnect.persistence.interf.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private AccessToken accessToken;
    private final PasswordValidator passwordValidator;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void updateUser(UpdateUserRequest request) {
        if(accessToken.getUserId().equals(request.getId())) {
            Optional<UserEntity> userOptional = userRepository.findById(request.getId());
            if (userOptional.isEmpty()) {
                throw new InvalidUserException("USER_ID_INVALID");
            }

            UserEntity user = userOptional.get();
            updateFields(request, user);
        } else {
            throw new UnauthorizedAccessException("USER_ID_NOT_FROM_LOGGED_IN_USER");
        }
    }

    private void updateFields(UpdateUserRequest request, UserEntity user){

        if(request.getUsername() != null){
            if(!userRepository.existsByUsername(request.getUsername())) {
                user.setUsername(request.getUsername());
            }
            else {
                throw new UsernameAlreadyExistsException();
            }
        }
        if(request.getEmail() != null) {
            if(!userRepository.existsByEmail(request.getEmail())) {
                user.setEmail(request.getEmail());
            }
            else {
                throw new EmailAlreadyExistsException();
            }
        }
        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
        }
        if(request.getPassword() != null){
            String errors = passwordValidator.validatePassword(request.getPassword());
            if(!errors.isEmpty()) {
                throw new InvalidPasswordException(errors);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

    }
}
