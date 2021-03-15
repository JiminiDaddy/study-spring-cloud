package com.chpark.msa.event.model;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/12
 * Time : 12:25 PM
 */

@Getter
public class CommentMessage implements Serializable {
    private String action;

    private Long postId;

    private Long commentId;

    private String author;

    private String comments;
}
