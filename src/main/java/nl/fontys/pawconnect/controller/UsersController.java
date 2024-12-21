package nl.fontys.pawconnect.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.interf.users.*;
import nl.fontys.pawconnect.domain.requests.users.CreateUserRequest;
import nl.fontys.pawconnect.domain.requests.users.GetUserRequest;
import nl.fontys.pawconnect.domain.requests.users.LoginUserRequest;
import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;
import nl.fontys.pawconnect.domain.responses.users.CreateUserResponse;
import nl.fontys.pawconnect.domain.responses.users.GetUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
//@CrossOrigin(origins = {"http://127.0.0.1:5173/"})
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    //Only testing
    private ChangeUserAvatarUseCase changeUserAvatarUseCase;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginUserRequest request) {
        String token = loginUserUseCase.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable(value = "id") final String userId) {
        GetUserResponse response = getUserUseCase.getUser(new GetUserRequest(userId));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") String id,
                                           @RequestBody @Valid UpdateUserRequest request) {
        updateUserUseCase.updateUser(id, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/avatar")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @PathVariable(value = "id") final String userId) {
        String filePath = changeUserAvatarUseCase.changeUserAvatar(file, userId);
        return ResponseEntity.ok().body(filePath);
    }
}
