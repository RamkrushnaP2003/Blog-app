package com.project.blogapp.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class LoginUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
