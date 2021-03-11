package com.chpark.msa.web.dto;

import com.chpark.msa.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:09 PM
 */

@NoArgsConstructor
@Getter
public class CommentResponseDto{
    private Long postId;

    private Long commentId;

    private String author;

    private String comments;

    @Builder
    public CommentResponseDto(Comment entity) {
       this.commentId = entity.getCommentId().getId();
       this.postId = entity.getCommentId().getPostId();
       this.author = entity.getAuthor();
       this.comments = entity.getComments();
    }
}
