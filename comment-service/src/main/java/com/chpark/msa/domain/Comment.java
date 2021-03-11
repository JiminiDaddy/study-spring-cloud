package com.chpark.msa.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:18 PM
 */

@Getter
@Table(name = "comments")
@Entity
public class Comment {
    @EmbeddedId
    private CommentId commentId;

    private String author;

    private String comments;

    private String password;

    protected Comment() {}

    @Builder
    public Comment(Long postId, Long commentId, String author, String comments, String password) {
        this.commentId = CommentId.builder().postId(postId).id(commentId).build();
        this.author = author;
        this.comments = comments;
        this.password = password;
    }
}
