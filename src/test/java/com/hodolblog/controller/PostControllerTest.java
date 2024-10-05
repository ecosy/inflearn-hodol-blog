package com.hodolblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodolblog.domain.Post;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired private ObjectMapper objectMapper;
    @Autowired private MockMvc mockMvc;
    @Autowired private PostRepository postRepository;

    @BeforeEach()
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 hello world를 출력한다.")
    void helloWorldPostTest() throws Exception {
        mockMvc.perform(get("/posts"))
               .andExpect(status().isOk())
               .andExpect(content().string("hello world!"))
               .andDo(print()); // request, response에 대한 요약을 보여준다.
    }

    @Test
    @DisplayName("POST basic test")
    void postBasicRequestTest() throws Exception {
        // given
        PostCreate request = PostCreate.of("제목 입니다.", "글 내용 입니다.");
        String json = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json))
               .andExpect(status().isOk())
               .andExpect(content().string(""))
               .andDo(print()); // request, response에 대한 요약을 보여준다.    }
    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void validateTitleNullWhenPosts() throws Exception {
        // given
        PostCreate request = PostCreate.of(null, "글 내용 입니다.");
        String json = objectMapper.writeValueAsString(request);

        // when, then
        mockMvc.perform(post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("400"))
               .andExpect(jsonPath("$.message").value("잘못된 요청 입니다."))
               .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
               .andDo(print()); // request, response에 대한 요약을 보여준다.    }
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void savePostDB() throws Exception {
        // given
        PostCreate request = PostCreate.of("제목 입니다.", "글 내용 입니다.");
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                                .contentType(APPLICATION_JSON)
                                .content(json))
               .andExpect(status().isOk())
               .andDo(print()); // request, response에 대한 요약을 보여준다.    }

        // then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post post = postRepository.findAll().getFirst();
        assertThat(post.getTitle()).isEqualTo("제목 입니다.");
        assertThat(post.getContent()).isEqualTo("글 내용 입니다.");
    }

    @Test
    @DisplayName("글 1개 조회")
    void getOnePost() throws Exception {
        // given
        Post samplePost = Post.builder()
                              .title("title")
                              .content("content")
                              .build();
        postRepository.save(samplePost);

        // when, then
        mockMvc.perform(get("/posts/{post_id}", samplePost.getId())
                                .contentType(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(samplePost.getId()))
               .andExpect(jsonPath("$.title").value(samplePost.getTitle()))
               .andExpect(jsonPath("$.content").value(samplePost.getContent()))
               .andDo(print());
    }
}