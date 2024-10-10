package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreate;
import com.hodolblog.request.PostSearch;
import com.hodolblog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        Post post = Post.builder()
                        .title(postCreate.title)
                        .content(postCreate.content)
                        .build();

        postRepository.save(post);
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                                  .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다."));

        return PostResponse.builder()
                           .id(post.getId())
                           .title(post.getTitle())
                           .content(post.getContent())
                           .build();
    }

    public List<PostResponse> getPosts(PostSearch postSearch) {
        return postRepository.getPosts(postSearch).stream()
                      .map(PostResponse::new)
                      .toList();
    }

//    public void editPost(Long postId, PostEdit postEdit) {
//        postRepository.findById(postId)
//                      .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다"));
//
//    }
}