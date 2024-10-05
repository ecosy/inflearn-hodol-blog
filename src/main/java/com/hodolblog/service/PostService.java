package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        // postCreate -> Entity 형태로 변형!
        Post post = Post.builder()
                        .title(postCreate.title)
                        .content(postCreate.content)
                        .build();

        postRepository.save(post);
    }

    public Post getPost(Long id) {
        Post post = postRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));
        return post;
    }
}