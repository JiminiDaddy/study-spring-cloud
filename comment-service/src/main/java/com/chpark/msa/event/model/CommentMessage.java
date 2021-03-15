package com.chpark.msa.event.model;

import com.chpark.msa.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/12
 * Time : 12:25 PM
 */

@Getter
public class CommentMessage {
    private String action;

    private Long postId;

    private Long commentId;

    private String author;

    private String comments;

    @Builder
    public CommentMessage(String action, Comment comment) {
        this.action = action;
        this.postId = comment.getCommentId().getPostId();
        this.commentId = comment.getCommentId().getId();
        this.author = comment.getAuthor();
        this.comments = comment.getComments();
    }

}
