package com.hodolblog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder @ToString
public class PostCreateRequest {
    @NotBlank(message = "타이틀을 입력해주세요.") public final String title;
    @NotBlank(message = "컨텐츠를 입력해주세요.") public final String content;

    public static PostCreateRequest of(String title, String content) {
        return PostCreateRequest.builder()
                                .title(title)
                                .content(content)
                                .build();
    }
}

