package nl.fontys.pawconnect.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.pawconnect.business.interf.users.CreateUserUseCase;
import nl.fontys.pawconnect.business.interf.users.GetUserUseCase;
import nl.fontys.pawconnect.business.interf.users.LoginUserUseCase;
import nl.fontys.pawconnect.business.interf.users.UpdateUserUseCase;
import nl.fontys.pawconnect.domain.requests.users.CreateUserRequest;
import nl.fontys.pawconnect.domain.requests.users.GetUserRequest;
import nl.fontys.pawconnect.domain.requests.users.LoginUserRequest;
import nl.fontys.pawconnect.domain.requests.users.UpdateUserRequest;
import nl.fontys.pawconnect.domain.responses.users.CreateUserResponse;
import nl.fontys.pawconnect.domain.responses.users.GetUserResponse;
import nl.fontys.pawconnect.domain.responses.users.LoginUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
//@CrossOrigin(origins = {"http://127.0.0.1:5173/"})
public class UsersController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid LoginUserRequest request){
        LoginUserResponse response = loginUserUseCase.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable(value = "id") final String userId){
        GetUserResponse response = getUserUseCase.getUser(new GetUserRequest(userId));
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUser(@PathVariable(value = "id") String id,
                                           @RequestBody @Valid UpdateUserRequest request){
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }
}
