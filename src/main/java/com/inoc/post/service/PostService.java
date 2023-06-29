package com.inoc.post.service;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import com.inoc.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);
        // DB 저장
        Post savePost = postRepository.save(post);
        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        // DB에 존재하는지 확인
        Post post = findPost(id);

        if (post.getPassword().equals(requestDto.getPassword())) {
            // post 내용 수정
            post.update(requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    public Long deletePost(Long id, PostRequestDto requestDto) {
        // DB에 존재하는지 확인
        Post post = findPost(id);

        if (post.getPassword().equals(requestDto.getPassword())) {
            postRepository.delete(post);
            return id;
        } else {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));
    }
}
