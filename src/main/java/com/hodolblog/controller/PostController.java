package com.hodolblog.controller;

import com.hodolblog.request.PostCreateRequest;
import com.hodolblog.request.PostEditRequest;
import com.hodolblog.request.PostSearch;
import com.hodolblog.response.PostResponse;
import com.hodolblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/hello-world")
    public String getHelloWorld() {
        return "hello world!";
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        postService.createPost(postCreateRequest);
    }

    @GetMapping("/posts/{post_id}")
    public PostResponse getPostById(@PathVariable(name = "post_id") Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts(@ModelAttribute PostSearch postSearch) {
        return postService.getPosts(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId,
                         @RequestBody @Valid PostEditRequest postEditRequest) {
        postService.editPost(postId, postEditRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public void editPost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}