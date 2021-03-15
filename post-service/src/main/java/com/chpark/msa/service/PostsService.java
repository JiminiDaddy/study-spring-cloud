package com.chpark.msa.service;

import com.chpark.msa.client.CommentClient;
import com.chpark.msa.domain.Posts;
import com.chpark.msa.event.model.CommentMessage;
import com.chpark.msa.repository.PostsRepository;
import com.chpark.msa.web.dto.PostsResponseDto;
import com.chpark.msa.web.dto.PostsSaveRequestDto;
import com.chpark.msa.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:05 PM
 */

@RequiredArgsConstructor
@Transactional
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final CommentClient commentClient;

    public PostsResponseDto save(PostsSaveRequestDto requestDto) {
        Posts posts = postsRepository.save(requestDto.toEntity());
        return PostsResponseDto.builder().entity(posts).build();
    }

    @Transactional(readOnly = true)
    public PostsResponseDto find(Long id) {
        Posts posts = findById(id);
        // 포스팅과 연결된 댓글 목록 가져오기
        List<CommentMessage> comments = findAllComments(id);
        PostsResponseDto responseDto = PostsResponseDto.builder().entity(posts).build();
        responseDto.addComments(comments);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public List<PostsResponseDto> findAll() {
        List<Posts> postsList = postsRepository.findAll();
        return postsList.stream().map(posts -> PostsResponseDto.builder().entity(posts).build()).collect(Collectors.toList());
    }

    public PostsResponseDto update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = findById(id);
        posts.update(requestDto.getTitle(), requestDto.getContents());
        posts = postsRepository.save(posts);
        return PostsResponseDto.builder().entity(posts).build();
    }

    public Long delete(Long id) {
        postsRepository.deleteById(id);
        return id;
    }

    private Posts findById(Long id) {
        return postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Wrong postsId: <" + id + ">"));
    }

    // TODO Comment-Service와 동기적인 통신이 이루어지므로 서비스 장애에 대비해 회로차단기와 같은 실패전략이 필요하다.
    private List<CommentMessage> findAllComments(Long postId) {
        return commentClient.findAllComments(postId);
    }
}
