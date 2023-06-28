package com.inoc.post.controller;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import com.inoc.post.service.PostService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    private final JdbcTemplate jdbcTemplate;

    public PostController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final Map<Long, Post> postList = new HashMap<>();

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        PostService postService = new PostService(jdbcTemplate);
        return postService.getPost();
    }

    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.createPost(requestDto);
    }

    @PutMapping("/post/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long deletePost(@PathVariable Long id) {
        PostService postService = new PostService(jdbcTemplate);
        return postService.deletePost(id);
    }
}