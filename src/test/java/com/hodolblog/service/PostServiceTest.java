package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 테스트")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                                          .title("제목 입니다.")
                                          .content("내용 입니다.")
                                          .build();
        // when
        postService.write(postCreate);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post post = postRepository.findAll().getFirst();
        assertThat(post.getTitle()).isEqualTo(postCreate.getTitle());
        assertThat(post.getContent()).isEqualTo(postCreate.getContent());
    }


    @Test
    @DisplayName("글 1개 조회")
    void getOnePostTest() {
        // given
        Post samplePost = Post.builder()
                              .title("title")
                              .content("content")
                              .build();
        postRepository.save(samplePost);

        // when
        Post savedPost = postService.getPost(samplePost.getId());

        // then
        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo(samplePost.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(samplePost.getContent());
    }
}