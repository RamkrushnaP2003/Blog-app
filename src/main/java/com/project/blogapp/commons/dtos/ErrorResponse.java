package com.project.blogapp.commons.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private Integer statusCode;
    private String message;
}
