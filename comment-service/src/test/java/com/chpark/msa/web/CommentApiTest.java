package com.chpark.msa.web;

import com.chpark.msa.web.dto.CommentResponseDto;
import com.chpark.msa.web.dto.CommentSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 10:55 PM
 */

@Slf4j
@SpringBootTest
class CommentApiTest {
    private static final String URL = "http://localhost:8080/api/v1/comments";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
       mvc = MockMvcBuilders.webAppContextSetup(context) .build();
    }

    @DisplayName("코멘트작성하기")
    @Test
    void saveTest() throws Exception {
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder().commentId(1L).comments("댓글작성").password("1234").author("chpark") .build();
        MvcResult result = mvc.perform(post(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk()).andReturn();
        String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        CommentResponseDto responseDto = new ObjectMapper().readValue(response, CommentResponseDto.class);
        log.info("postId: <{}>, commentsId:<{}>, author:<{}>, comments:<{}>", responseDto.getPostId(), responseDto.getCommentId(), responseDto.getAuthor(), responseDto.getComments());
        Assertions.assertEquals(1, responseDto.getPostId());
        Assertions.assertEquals(1, responseDto.getCommentId());
        Assertions.assertEquals("chpark", responseDto.getAuthor());

        requestDto = CommentSaveRequestDto.builder().commentId(2L).comments("댓글작성2").password("5678").author("chpark") .build();
        result = mvc.perform(post(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk()).andReturn();
        response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        responseDto = new ObjectMapper().readValue(response, CommentResponseDto.class);
        log.info("postId: <{}>, commentsId:<{}>, author:<{}>, comments:<{}>", responseDto.getPostId(), responseDto.getCommentId(), responseDto.getAuthor(), responseDto.getComments());
        Assertions.assertEquals(1, responseDto.getPostId());
        Assertions.assertEquals(2, responseDto.getCommentId());
        Assertions.assertEquals("chpark", responseDto.getAuthor());
    }
}
