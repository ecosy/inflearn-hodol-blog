package com.hodolblog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title;
    @Lob private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void edit(PostEditor postEditor) {
        Optional.ofNullable(postEditor.getTitle()).ifPresent(updatedTitle -> this.title = updatedTitle);
        Optional.ofNullable(postEditor.getContent()).ifPresent(updatedContent -> this.content = updatedContent);
    }
}