package com.inoc.post.service;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import com.inoc.post.repository.PostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostService {

    private final JdbcTemplate jdbcTemplate;

    public PostService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PostResponseDto> getPost() {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        return postRepository.findAll();
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post();
        // DB 저장
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        Post savePost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    public Long updatePost(Long id, PostRequestDto requestDto) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        // DB에 존재하는지 확인
        Post post = postRepository.findById(id);
        if(post != null) {
            // post 내용 수정
            postRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("존재하지 않습니다.");
        }
    }

    public Long deletePost(Long id) {
        PostRepository postRepository = new PostRepository(jdbcTemplate);
        // DB에 존재하는지 확인
        Post post = postRepository.findById(id);
        if(post != null) {
            // post 삭제
            postRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("존재하지 않습니다.");
        }
    }
}
