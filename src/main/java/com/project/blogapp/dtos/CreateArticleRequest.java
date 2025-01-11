package com.project.blogapp.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateArticleRequest {
    @NotNull
    private String title;
    @NotNull
    private String body;
    private String subTitle;
}
