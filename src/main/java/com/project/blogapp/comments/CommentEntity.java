package com.project.blogapp.comments;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import com.project.blogapp.articles.ArticleEntity;
import com.project.blogapp.user.UserEntity;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "comments")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = false)
    @NotNull
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "articleId", nullable = false)
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}
