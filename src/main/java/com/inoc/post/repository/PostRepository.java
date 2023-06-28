package com.inoc.post.repository;

import com.inoc.post.dto.PostRequestDto;
import com.inoc.post.dto.PostResponseDto;
import com.inoc.post.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class PostRepository {

    private  final JdbcTemplate jdbcTemplate;
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Post save(Post post) {
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO post (author, title, content) VALUES (?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, post.getAuthor());
                    preparedStatement.setString(2, post.getTitle());
                    preparedStatement.setString(3, post.getContent());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        post.setId(id);

        return post;
    }

    public List<PostResponseDto> findAll() {
        // DB 조회
        String sql = "SELECT * FROM post";

        return jdbcTemplate.query(sql, new RowMapper<PostResponseDto>() {
            @Override
            public PostResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Post 데이터들을 PostResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String content = rs.getString("content");
                return new PostResponseDto(id, author, title, content);
            }
        });
    }


    public void update(Long id, PostRequestDto requestDto) {
        String sql = "UPDATE post SET author = ?, title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, requestDto.getAuthor(), requestDto.getTitle(), requestDto.getContent(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM post WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Post findById(Long id) {
        // DB 조회
        String sql = "SELECT * FROM post WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if(resultSet.next()) {
                Post post = new Post();
                post.setAuthor(resultSet.getString("author"));
                post.setTitle(resultSet.getString("title"));
                post.setContent(resultSet.getString("content"));
                return post;
            } else {
                return null;
            }
        }, id);
    }
}
