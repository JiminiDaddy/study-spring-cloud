package com.chpark.msa.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:22 PM
 */

@Getter
@Embeddable
public class CommentId implements Serializable {
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "comment_id")
    private Long id;

    protected CommentId() {}

    @Builder
    public CommentId(Long id, Long postId) {
        this.id = id;
        this.postId = postId;
    }
}
