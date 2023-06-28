package com.inoc.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String author;
    private String password;
    private String title;
    private String content;
}
