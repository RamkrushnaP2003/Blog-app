package com.project.blogapp.comments;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/articles/{article-slug}/comments")
public class CommentController {
    @GetMapping("")
    public String getMethodName() {
        return new String("Hello Comments");
    }
    
}
