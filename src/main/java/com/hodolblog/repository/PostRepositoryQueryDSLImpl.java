package com.hodolblog.repository;

import com.hodolblog.domain.Post;
import com.hodolblog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.hodolblog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryQueryDSLImpl implements PostRepositoryQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getPosts(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                              .limit(postSearch.getSize())
                              .offset(postSearch.getOffset())
                              .orderBy(post.id.desc())
                              .fetch();
    }
}