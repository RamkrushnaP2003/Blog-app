package com.project.blogapp.articles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    @GetMapping("")
    public String getMethodName() {
        return new String("Hello Articles");
    }
    
}
