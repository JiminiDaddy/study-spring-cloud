package com.chpark.msa.web;

import com.chpark.msa.domain.Posts;
import com.chpark.msa.repository.PostsRepository;
import com.chpark.msa.web.dto.PostsResponseDto;
import com.chpark.msa.web.dto.PostsSaveRequestDto;
import com.chpark.msa.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 4:17 PM
 */

@Slf4j
@Transactional
@EnableJpaAuditing
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsApiTest {
    private static final String URL = "http://localhost:8080";

    private MockMvc mvc;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @DisplayName("등록하기")
    @Test
    public void saveTest() throws Exception {
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title("제목 ").contents("내용").author("chpark").build();
        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());

        List<Posts> postsList = postsRepository.findAll();
        Assertions.assertEquals(1, postsList.size());
        Posts savedPosts = postsList.get(0);
        log.info("Saved Id: <{}>", savedPosts.getId());
        Assertions.assertNotNull(savedPosts.getId());
    }

    @DisplayName("조회하기 ")
    @Test
    public void findTest() throws Exception {
        log.info("count: {}", postsRepository.count());
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title("제목 ").contents("내용").author("chpark").build();
        mvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk());
        log.info("count: {}", postsRepository.count());
        List<Posts> postsList = postsRepository.findAll();
        Long id = postsList.get(0).getId();


        MvcResult result = mvc.perform(get(URL + "/" + String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        //log.info("Response: <{}>", result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        log.info("Response: <{}>", result.getResponse().getContentAsString());
    }

    @DisplayName("수정하기 ")
    @Test
    public void updateTest() throws Exception {
        saveTest();
        List<Posts> postsList = postsRepository.findAll();
        Long id = postsList.get(0).getId();

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title("New-Title").contents("New-Contents").build();
        MvcResult result = mvc.perform(put(URL + "/" + String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        ).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        log.info("Response: <{}>", result.getResponse().getContentAsString());
        String contents = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        PostsResponseDto responseDto = mapper.readValue(contents, PostsResponseDto.class);
        Assertions.assertEquals("New-Title", responseDto.getTitle());
        Assertions.assertEquals("New-Contents", responseDto.getContents());
    }

    @DisplayName("삭제하기")
    @Test
    public void deleteTest() throws Exception {
        saveTest();
        List<Posts> postsList = postsRepository.findAll();
        Long id = postsList.get(0).getId();

        MvcResult result = mvc.perform(delete(URL + "/" + String.valueOf(id))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

        Assertions.assertEquals(0, postsRepository.count());
    }
}
