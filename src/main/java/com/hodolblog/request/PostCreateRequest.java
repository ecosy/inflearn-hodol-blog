package com.hodolblog.request;

import com.hodolblog.exception.InvalidRequestException;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
public class PostCreateRequest {
    @NotBlank(message = "타이틀을 입력해주세요.") public final String title;
    @NotBlank(message = "컨텐츠를 입력해주세요.") public final String content;

    public static PostCreateRequest of(String title, String content) {
        return PostCreateRequest.builder()
                                .title(title)
                                .content(content)
                                .build();
    }

    public void validate() {
        if (this.title.contains("바보")) {
            throw new InvalidRequestException("title", "사용할 수 없는 단어('바보') 가 포함되어 있습니다.");
        }
    }
}

