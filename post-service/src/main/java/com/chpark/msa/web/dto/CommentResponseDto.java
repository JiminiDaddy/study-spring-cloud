package com.chpark.msa.web.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 11:01 PM
 */

@Getter
@Builder
class CommentResponseDto {
    private Long commentId;

    private String author;

    private String comments;
}
