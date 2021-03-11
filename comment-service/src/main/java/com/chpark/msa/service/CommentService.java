package com.chpark.msa.service;

import com.chpark.msa.domain.Comment;
import com.chpark.msa.repository.CommentsRepository;
import com.chpark.msa.web.dto.CommentResponseDto;
import com.chpark.msa.web.dto.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:15 PM
 */

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentsRepository commentsRepository;

    public CommentResponseDto save(Long postId, CommentSaveRequestDto requestDto) {
        Comment comment = Comment.builder()
                .postId(postId)
                .commentId(requestDto.getCommentId())
                .author(requestDto.getAuthor())
                .comments(requestDto.getComments())
                .password(requestDto.getPassword()).build();
        return CommentResponseDto.builder().entity(commentsRepository.save(comment)).build();
    }
}
