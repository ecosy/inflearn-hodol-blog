package com.hodolblog.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hodolblog.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    @JsonIgnore public static final int TITLE_MAX_LENGTH = 10;

    private final Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), TITLE_MAX_LENGTH));
        this.content = content;
    }
}