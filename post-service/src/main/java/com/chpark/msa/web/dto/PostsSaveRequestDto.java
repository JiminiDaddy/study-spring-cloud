package com.chpark.msa.web.dto;

import com.chpark.msa.domain.Posts;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:07 PM
 */

@Builder
@Getter
public class PostsSaveRequestDto {
    private String title;

    private String contents;

    private String author;

    public Posts toEntity() {
        return Posts.builder().title(title).contents(contents).author(author).build();
    }
}
