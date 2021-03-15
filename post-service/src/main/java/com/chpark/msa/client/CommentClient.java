package com.chpark.msa.client;

import com.chpark.msa.event.model.CommentMessage;
import com.chpark.msa.repository.CommentRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 4:32 PM
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class CommentClient {
    private final CommentRedisRepository commentRedisRepository;
    private final RestTemplate restTemplate;
    private static final String COMMENTS_SERVICE_URL = "http://localhost:8080/api/v1/comments";

    public List<CommentMessage> findAllComments(Long postId) {
        log.debug("findAllComments, postId:<{}>", postId);
        // TODO null -> Optional로 변환
        List<CommentMessage> comments = findAllCommentsRedisCache(postId);
        if (comments != null) {
            log.debug("Find allComments, by cached");
            return comments;
        }
        log.debug("Cannot find comment by redis-cache. So try to find from Comment-service");
        // TODO RestTemplate -> OpenFeign 전환, 코드가 많이 지저분함
        ResponseEntity<List> restExchange = restTemplate.exchange(
                COMMENTS_SERVICE_URL + "/{postId}",
                HttpMethod.GET,
                null,
                List.class,
                postId);

        comments = (List<CommentMessage>) restExchange.getBody();
        if (comments == null) {
            log.info("Comments is empty");
            return new ArrayList<>();
        }

        saveComment(postId, comments);
        return commentRedisRepository.findAllComments(postId);
    }

    private List<CommentMessage> findAllCommentsRedisCache(Long postId) {
       return commentRedisRepository.findAllComments(postId);
    }

    private void saveComment(Long postId, List<CommentMessage> commentMessages) {
       commentRedisRepository.saveAllComments(postId, commentMessages);
    }
}
