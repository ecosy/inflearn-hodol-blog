package com.hodolblog.repository;

import com.hodolblog.domain.Post;
import com.hodolblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryQueryDSL {
    List<Post> getPosts(PostSearch postSearch);
}
