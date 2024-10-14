package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.domain.PostEditor;
import com.hodolblog.exception.PostNotFoundException;
import com.hodolblog.repository.PostRepository;
import com.hodolblog.request.PostCreateRequest;
import com.hodolblog.request.PostEditRequest;
import com.hodolblog.request.PostSearch;
import com.hodolblog.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostCreateRequest postCreateRequest) {
        Post post = Post.builder()
                        .title(postCreateRequest.title)
                        .content(postCreateRequest.content)
                        .build();

        postRepository.save(post);
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                                  .orElseThrow(PostNotFoundException::new);

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

    @Transactional
    public void editPost(Long postId, PostEditRequest postEditRequest) {
        Post post = postRepository.findById(postId)
                                           .orElseThrow(PostNotFoundException::new);

        PostEditor postEditor = PostEditor.of(postEditRequest);
        post.edit(postEditor);
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                                  .orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
    }
}