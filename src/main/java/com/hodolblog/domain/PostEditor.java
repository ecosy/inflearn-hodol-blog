package com.hodolblog.domain;

import com.hodolblog.request.PostEditRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {
    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static PostEditor of(PostEditRequest postEditRequest) {
        return PostEditor.builder()
                         .title(postEditRequest.getTitle())
                         .content(postEditRequest.getContent())
                         .build();
    }
}