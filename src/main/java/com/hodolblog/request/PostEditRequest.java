package com.hodolblog.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PostEditRequest {
    @NotBlank private final Long postId;
    @NotBlank(message = "타이틀을 입력하세요.") private final String title;
    @NotBlank(message = "콘텐츠를 입력하세요.") @Lob private final String content;

    @Builder
    public PostEditRequest(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
