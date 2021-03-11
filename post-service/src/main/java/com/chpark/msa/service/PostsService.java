package com.chpark.msa.service;

import com.chpark.msa.domain.Posts;
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

    public PostsResponseDto save(PostsSaveRequestDto requestDto) {
        Posts posts = postsRepository.save(requestDto.toEntity());
        return PostsResponseDto.builder().entity(posts).build();
    }

    @Transactional(readOnly = true)
    public PostsResponseDto find(Long id) {
        Posts posts = findById(id);
        return PostsResponseDto.builder().entity(posts).build();
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
}
