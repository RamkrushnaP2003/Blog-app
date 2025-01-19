package com.project.blogapp.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.blogapp.commons.dtos.ErrorResponse;
import com.project.blogapp.dtos.CreateUserRequest;
import com.project.blogapp.dtos.UserResponse;
import com.project.blogapp.security.JWTUtil;
import com.project.blogapp.user.UserService.UserNotFoundException;
import com.project.blogapp.dtos.LoginUserRequest;
import java.net.URI;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;

    public UserController(UserService userService, ModelMapper modelMapper, JWTUtil jwtUtil) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest user) {
        UserEntity savedUser = userService.createUser(user);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser, UserResponse.class));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest user) {
        // Authenticate the user
        UserEntity savedUser = userService.loginUser(user.getUsername(), user.getPassword());

        // Generate JWT token with role
        String token = jwtUtil.generateToken(savedUser.getUsername(), savedUser.getRole().name());

        // Add token to the response
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(token);

        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserEntity>> getAppUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserByUserByuserId(@PathVariable Long userId) {
        UserEntity user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    

    @ExceptionHandler({UserService.InvalidCreditialsException.class, UserService.UserNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserException(Exception ex) {
        String message;
        HttpStatus status;

        if (ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidCreditialsException) {
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response = ErrorResponse.builder()
            .message(message)
            .statusCode(status.value())
            .build();

        return ResponseEntity.status(status).body(response);
    }
}
