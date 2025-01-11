package com.project.blogapp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/test/")
public class TestController {
    @GetMapping("/hello")
    public String getMethodName() {
        return new String("Hello");
    }
    
}
