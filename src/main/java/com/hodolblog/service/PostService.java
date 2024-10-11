package com.hodolblog.service;

import com.hodolblog.domain.Post;
import com.hodolblog.domain.PostEditor;
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

    public void write(PostCreateRequest postCreateRequest) {
        Post post = Post.builder()
                        .title(postCreateRequest.title)
                        .content(postCreateRequest.content)
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

    @Transactional
    public void editPost(Long postId, PostEditRequest postEditRequest) {
        Post post = postRepository.findById(postId)
                                           .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 입니다"));

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder.title(postEditRequest.getTitle())
                                             .content(post.getContent())
                                             .build();
        post.edit(postEditor);
        postRepository.save(post);
    }
}