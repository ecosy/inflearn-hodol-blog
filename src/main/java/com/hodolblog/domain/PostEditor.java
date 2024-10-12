package com.hodolblog.domain;

import com.hodolblog.request.PostEditRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

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
        PostEditorBuilder builder = PostEditor.builder();

        Optional.ofNullable(postEditRequest.getTitle()).ifPresent(builder::title);
        Optional.ofNullable(postEditRequest.getContent()).ifPresent(builder::content);

        return builder.build();
    }
}