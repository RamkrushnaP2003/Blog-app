package com.project.blogapp.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogapp.commons.dtos.ErrorResponse;
import com.project.blogapp.dtos.CreateUserRequest;
import com.project.blogapp.dtos.UserResponse;
import com.project.blogapp.user.UserService.UserNotFoundException;
import com.project.blogapp.dtos.LoginUserRequest;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/users") 
public class UserController {
    private final UserService userService;
    private final ModelMapper  modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest user) {
        UserEntity savedUser = userService.createUser(user);
        URI savedUserUri = URI.create("/users/"+savedUser.getId());
        return ResponseEntity.created(savedUserUri).body(modelMapper.map(savedUser, UserResponse.class));
    }
    
    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest user) {
        UserEntity savedUser = userService.loginUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(modelMapper.map(savedUser, UserResponse.class));
    }

    @ExceptionHandler(UserNotFoundException.class) 
    ResponseEntity<ErrorResponse> handleUserNotFoundExceptio(Exception ex) {
        String message;
        HttpStatus status;

        if(ex instanceof UserService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
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
