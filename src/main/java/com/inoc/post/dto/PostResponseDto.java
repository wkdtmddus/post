package com.inoc.post.dto;

import com.inoc.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public PostResponseDto(Long id, String author, String title, String content) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
