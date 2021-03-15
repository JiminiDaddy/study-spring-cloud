package com.chpark.msa.web.dto;

import com.chpark.msa.domain.Posts;
import com.chpark.msa.event.model.CommentMessage;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:09 PM
 */

@NoArgsConstructor
@Getter
public class PostsResponseDto {
    private Long id;

    private String title;

    private String contents;

    private String author;

    private List<CommentResponseDto> comments;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;

    @Builder
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.author = entity.getAuthor();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    // TODO 생성자에서 처리하는 구조가 맞지 않을까?
    public void addComments(List<CommentMessage> comments) {
       this.comments = comments.stream().map(comment ->
               CommentResponseDto.builder()
                       .commentId(comment.getCommentId())
                       .author(comment.getAuthor())
                       .comments(comment.getComments()).build())
               .collect(Collectors.toList());
    }
}
