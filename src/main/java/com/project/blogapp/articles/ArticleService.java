package com.project.blogapp.articles;

import org.springframework.stereotype.Service;

import com.project.blogapp.dtos.CreateArticleRequest;
import com.project.blogapp.user.UserRepository;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        // Use findBySlug instead of findByUsing
        var article = articleRepository.findBySlug(slug);
        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = userRepository.findById(authorId).orElseThrow(() -> new Error("User with id: " + authorId + " Not Found"));
        return articleRepository.save(ArticleEntity.builder()
            .title(a.getTitle())
            .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
            .body(a.getBody())
            .subtitle(a.getSubTitle())
            .author(author)
            .build());
    }

    public ArticleEntity updateArticle(Long articleId, CreateArticleRequest a) {
        var article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if (a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }
        if (a.getBody() != null) {
            article.setBody(a.getBody());
        }
        if (a.getSubTitle() != null) {
            article.setSubtitle(a.getSubTitle());
        }
        return articleRepository.save(article);
    }

    class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug: " + slug + " Not Found");
        }

        public ArticleNotFoundException(Long articleId) {
            super("Article with id: " + articleId + " Not Found");
        }
    }
}
