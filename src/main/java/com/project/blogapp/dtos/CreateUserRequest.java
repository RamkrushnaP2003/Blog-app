package com.project.blogapp.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
