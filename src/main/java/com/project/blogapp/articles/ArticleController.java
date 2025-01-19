package com.project.blogapp.articles;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/all-articles")
    public ResponseEntity<List<ArticleEntity>> getAllArticle() {
        List<ArticleEntity> articles = (List<ArticleEntity>)articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }
    
}
