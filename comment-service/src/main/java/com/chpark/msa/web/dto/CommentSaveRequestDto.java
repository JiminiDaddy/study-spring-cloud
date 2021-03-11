package com.chpark.msa.web.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 11:01 PM
 */

@Builder
@Getter
public class CommentSaveRequestDto {
    private Long commentId;

    private String author;

    private String comments;

    // TODO Encrypt
    private String password;
}
