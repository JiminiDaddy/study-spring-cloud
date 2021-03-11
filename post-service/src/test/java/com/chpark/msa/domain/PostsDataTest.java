package com.chpark.msa.domain;

import com.chpark.msa.repository.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/11
 * Time : 3:55 PM
 */

@Slf4j
@EnableJpaAuditing
@Transactional
@DataJpaTest
public class PostsDataTest {
    @Autowired
    private PostsRepository postsRepository;

    @DisplayName("등록하기")
    @Test
    public void saveTest() {
        Posts posts = createPosts("제목", "내용", "chpark");
        Assertions.assertEquals(1, postsRepository.count());
    }

    @DisplayName("조회하기")
    @Test
    public void findTest() {
        Posts posts = createPosts("제목", "내용", "chpark");
        Assertions.assertEquals(1, postsRepository.count());
        Posts findPosts = find(posts);
        Assertions.assertEquals(posts.getAuthor(), findPosts.getAuthor());
        log.info("Created:<{}>, Modified:<{}>", posts.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), posts.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        posts = createPosts("제목2", "내용2", "chpark2");
        Assertions.assertEquals(2, postsRepository.count());
        findPosts = find(posts);
        Assertions.assertEquals(posts.getTitle(), findPosts.getTitle());

        List<Posts> postsList = postsRepository.findAll();
        Assertions.assertEquals(2, postsList.size());
    }

    @DisplayName("수정하기 ")
    @Test
    public void updateTest() {
        Posts posts = createPosts("제목", "내용", "chpark");
        Assertions.assertEquals(1, postsRepository.count());
        Posts findPosts = find(posts);
        Assertions.assertEquals(posts.getAuthor(), findPosts.getAuthor());
        findPosts.update("수정된제목", "수정된내용");
        Posts updatePosts = postsRepository.save(findPosts);
        Assertions.assertEquals("수정된제목", updatePosts.getTitle());
        Assertions.assertEquals("수정된내용", updatePosts.getContents());
        log.info("Created:<{}>, Modified:<{}>", posts.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), posts.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @DisplayName("삭제하기")
    @Test
    public void deleteTest() {
        Posts posts = createPosts("제목", "내용", "chpark");
        Assertions.assertEquals(1, postsRepository.count());
        Posts findPosts = find(posts);
        Assertions.assertEquals(posts.getAuthor(), findPosts.getAuthor());
        postsRepository.deleteById(findPosts.getId());
        Assertions.assertEquals(0, postsRepository.count());
    }

    private Posts find(Posts posts) {
        return postsRepository.findById(posts.getId()).orElseThrow(() -> new IllegalArgumentException("WrongId"));
    }

    private Posts createPosts(String title, String contents, String author) {
        Posts posts = Posts.builder().title(title).contents(contents).author(author).build();
        return postsRepository.save(posts);
    }
}
