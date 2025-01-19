package com.project.blogapp.user;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blogapp.dtos.CreateUserRequest;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }

    public UserEntity createUser(CreateUserRequest req) {
        UserEntity newUser = modelMapper.map(req, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        return userRepository.save(newUser);
    }

    public UserEntity getUser(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username, String password) {
        var user = userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        boolean passMatch= passwordEncoder.matches(password, user.getPassword());
        if(!passMatch) {
            throw new InvalidCreditialsException();
        }
        return user;
    }

    public class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with name: " + username + " Not Found");
        }

        public UserNotFoundException(Long authorId) {
            super("User with id: " + authorId + " Not Found");
        }
    }

    public class InvalidCreditialsException extends IllegalArgumentException {
        public InvalidCreditialsException() {
            super("Invalid username or password combination");
        }        
    }
}
