package com.project.blogapp.users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.project.blogapp.dtos.CreateUserRequest;
import com.project.blogapp.user.UserService;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void can_create_user() {
        var user = userService.createUser(new CreateUserRequest(
            "john",
            "pass123",
            "john@gmail.com"
        ));
        Assertions.assertNotNull(user, "User should not be null");
        Assertions.assertEquals("john", user.getUsername());
        Assertions.assertEquals("john@gmail.com", user.getEmail());
    }
}
