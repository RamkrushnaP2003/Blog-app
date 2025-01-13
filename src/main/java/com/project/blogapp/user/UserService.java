package com.project.blogapp.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.blogapp.dtos.CreateUserRequest;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserEntity createUser(CreateUserRequest req) {
        UserEntity newUser = modelMapper.map(req, UserEntity.class);
        // TODO: encrypt and save password
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
        // TODO : check the password
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
}
