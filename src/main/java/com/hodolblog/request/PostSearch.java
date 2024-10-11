package com.hodolblog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Builder
@Getter@Setter
public class PostSearch {
    private static final int MAX_SIZE = 2000;
    @Builder.Default private int page = 1;
    @Builder.Default private int size = 10;

    public PostSearch(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public long getOffset() {
        return ((long) (max(1, page) - 1) * min(size, MAX_SIZE));
    }
}