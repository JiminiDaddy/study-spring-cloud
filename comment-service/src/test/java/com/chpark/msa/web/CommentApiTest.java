package com.chpark.msa.web;

import com.chpark.msa.web.dto.CommentResponseDto;
import com.chpark.msa.web.dto.CommentSaveRequestDto;
import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 10:55 PM
 */

@Slf4j
@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentApiTest {
    private static final String URL = "http://localhost:8080";

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
        String response = save(requestDto, 1L);
        CommentResponseDto responseDto = new ObjectMapper().readValue(response, CommentResponseDto.class);

        log.info("postId: <{}>, commentsId:<{}>, author:<{}>, comments:<{}>", responseDto.getPostId(), responseDto.getCommentId(), responseDto.getAuthor(), responseDto.getComments());
        Assertions.assertEquals(1L, responseDto.getPostId().longValue());
        Assertions.assertEquals(1, responseDto.getCommentId().longValue());
        Assertions.assertEquals("chpark", responseDto.getAuthor());

        requestDto = CommentSaveRequestDto.builder().commentId(2L).comments("댓글작성2").password("5678").author("chpark") .build();
        response = save(requestDto, 1L);
        responseDto = new ObjectMapper().readValue(response, CommentResponseDto.class);

        log.info("postId: <{}>, commentsId:<{}>, author:<{}>, comments:<{}>", responseDto.getPostId(), responseDto.getCommentId(), responseDto.getAuthor(), responseDto.getComments());
        Assertions.assertEquals(1, responseDto.getPostId().longValue());
        Assertions.assertEquals(2, responseDto.getCommentId().longValue());
        Assertions.assertEquals("chpark", responseDto.getAuthor());
    }

    @DisplayName("조회하기")
    @Test
    void findTest() throws Exception {
        CommentSaveRequestDto requestDto = CommentSaveRequestDto.builder().commentId(1L).comments("댓글작성!!").password("1234").author("chpark") .build();

        String response = save(requestDto, 1L);
        CommentResponseDto responseDto = new ObjectMapper().readValue(response, CommentResponseDto.class);
        log.info("postId: <{}>, commentsId:<{}>, author:<{}>, comments:<{}>",
                responseDto.getPostId(), responseDto.getCommentId(), responseDto.getAuthor(), responseDto.getComments());
        Assertions.assertEquals(1L, responseDto.getPostId().longValue());

        response = find(1L);
        Log.info("response: <{}>", response);
        List<CommentResponseDto> responseDtos = new ObjectMapper().readValue(
                response,
                new TypeReference<List<CommentResponseDto>>(){});
        Assertions.assertNotNull(responseDtos);

        Assertions.assertEquals("chpark", responseDtos.get(0).getAuthor());
        Assertions.assertEquals("댓글작성!!", responseDtos.get(0).getComments());
    }

    private String save(CommentSaveRequestDto requestDto, Long postId) throws Exception {
        MvcResult result = mvc.perform(post(URL + "/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk()).andReturn();
        // SpringBoot version변경으로 인해 오류발생해서 잠시 주석처리함. 다시 확인필요
        //response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return result.getResponse().getContentAsString();
    }

    private String find(Long postId) throws Exception {
        MvcResult result = mvc.perform(get(URL + "/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        //response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        return result.getResponse().getContentAsString();
    }
}
