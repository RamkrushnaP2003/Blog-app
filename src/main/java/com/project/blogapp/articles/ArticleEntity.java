package com.project.blogapp.articles;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

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

@Entity(name = "articles")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    @NotNull
    private String slug;

    @Column(nullable = true)
    @Nullable
    private String subtitle;

    @Column(nullable = true)
    @Nullable
    private String body;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "authorId", nullable = false)
    private UserEntity author;
}
