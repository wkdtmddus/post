package com.inoc.post.controller;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    private final Map<Long, Post> postList = new HashMap<>();

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        List<PostResponseDto> responseList = postList.values().stream().map(PostResponseDto::new).toList();
        return responseList;
    }

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        Post post = new Post(requestDto);

        Long maxId = postList.size() > 0 ? Collections.max(postList.keySet()) + 1 : 1;
        post.setId(maxId);

        postList.put(post.getId(), post);

        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }
}