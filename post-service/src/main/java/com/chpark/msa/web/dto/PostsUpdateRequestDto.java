package com.chpark.msa.web.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:32 PM
 */

@Builder
@Getter
public class PostsUpdateRequestDto {
    private String title;

    private String contents;
}
