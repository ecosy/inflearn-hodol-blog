package com.hodolblog.controller;

import com.hodolblog.request.PostCreate;
import com.hodolblog.response.PostResponse;
import com.hodolblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public String getPosts() {
        return "hello world!";
    }

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate postCreate) {
        log.info("params={}", postCreate.toString());
        postService.write(postCreate);
    }

    @GetMapping("/posts/{post_id}")
    public PostResponse getPost(@PathVariable(name = "post_id") Long id) {
        PostResponse postResponse = postService.getPost(id);
        return postResponse;
    }
}
