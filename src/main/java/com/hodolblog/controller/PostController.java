package com.hodolblog.controller;

import com.hodolblog.request.PostCreate;
import com.hodolblog.response.PostResponse;
import com.hodolblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public void post(@RequestBody @Valid PostCreate postCreate) {
        postService.write(postCreate);
    }

    @GetMapping("/posts/{post_id}")
    public PostResponse getPostById(@PathVariable(name = "post_id") Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts(Pageable page) {
        return postService.getPosts(page);
    }
}