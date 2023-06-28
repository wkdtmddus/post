package com.inoc.post.service;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import com.inoc.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> getPost() {
        return postRepository.findAll();
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post();
        // DB 저장
        Post savePost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    public Long updatePost(Long id, PostRequestDto requestDto) {
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
