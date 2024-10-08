package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreate;
import com.hodolblog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.IntStream;

import static com.hodolblog.service.PostService.PAGE_SIZE;
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
        PostResponse postResponse = postService.getPostById(samplePost.getId());

        // then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getTitle()).isEqualTo(samplePost.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(samplePost.getContent());
    }

    @Test
    @DisplayName("글 1개 조회 - 제목이 TITLE_MAX_LENGTH 글자 이상인 경우 10글자만 조회 확인")
    void getOnePost_whenTitleLengthMoreThanMax() {
        // given
        Post samplePost = Post.builder()
                              .title("t".repeat(PostResponse.TITLE_MAX_LENGTH + 1))
                              .content("content")
                              .build();
        postRepository.save(samplePost);

        // when
        PostResponse postResponse = postService.getPostById(samplePost.getId());

        // then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getTitle()).isEqualTo(samplePost.getTitle().substring(0, PostResponse.TITLE_MAX_LENGTH));
        assertThat(postResponse.getContent()).isEqualTo(samplePost.getContent());
    }

    @Test
    @DisplayName("글 1개 조회 - 제목이 TITLE_MAX_LENGTH 글자 미만인 경우, 해당 글자 수 만큼 조회 확인")
    void getOnePost_whenTitleLengthLessThanMax() {
        // given
        Post samplePost = Post.builder()
                              .title("t".repeat(PostResponse.TITLE_MAX_LENGTH - 1))
                              .content("content")
                              .build();
        postRepository.save(samplePost);

        // when
        PostResponse postResponse = postService.getPostById(samplePost.getId());

        // then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getTitle()).isEqualTo(samplePost.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(samplePost.getContent());
    }

    @Test
    @DisplayName("글 1개 조회 - 제목이 TITLE_MAX_LENGTH 글자 수 동일한 경우, 해당 글자 수 만큼 조회 확인")
    void getOnePost_whenTitleLengthEqualToMax() {
        // given
        Post samplePost = Post.builder()
                              .title("t".repeat(PostResponse.TITLE_MAX_LENGTH))
                              .content("content")
                              .build();
        postRepository.save(samplePost);

        // when
        PostResponse postResponse = postService.getPostById(samplePost.getId());

        // then
        assertThat(postResponse).isNotNull();
        assertThat(postResponse.getTitle()).isEqualTo(samplePost.getTitle());
        assertThat(postResponse.getContent()).isEqualTo(samplePost.getContent());
    }

    @Test
    @DisplayName("글 다건 조회 - 1 page")
    void getMultiPosts() {
        // given
        List<Post> requestPosts = IntStream.rangeClosed(1, 30)
                                           .mapToObj(i -> Post.builder()
                                                              .title("title " + i)
                                                              .content("content " + i)
                                                              .build())
                                           .toList();
        postRepository.saveAll(requestPosts);

        // when
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "id"));
        List<PostResponse> posts = postService.getPosts(pageRequest);

        // then
        assertThat(posts).hasSize(PAGE_SIZE);
        assertThat(posts.getFirst().getTitle()).isEqualTo("title " + 30);
        assertThat(posts.getLast().getTitle()).isEqualTo("title " + (30 - PAGE_SIZE + 1));
    }
}