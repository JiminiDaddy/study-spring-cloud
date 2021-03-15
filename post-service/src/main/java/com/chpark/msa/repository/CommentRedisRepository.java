package com.chpark.msa.repository;

import com.chpark.msa.event.model.CommentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 3:49 PM
 */

@Slf4j
@RequiredArgsConstructor
@Repository
public class CommentRedisRepository {
    private static final String COMMENT_KEY = "COMMENT";

    private static final String KEY_PREFIX = "POST";

    private final RedisTemplate<String, List<CommentMessage>> redisTemplate;

    private HashOperations<String, String, List<CommentMessage>> hashOperations;

    public List<CommentMessage> findAllComments(Long postId) {
        List<CommentMessage> allComments = hashOperations.get(COMMENT_KEY, createKey(postId));
        return Optional.ofNullable(allComments).orElseGet(ArrayList::new);
    }

    public void saveComment(CommentMessage commentMessage) {
        log.debug("saveComment, postId:<{}>, commentId:<{}>, comment:<{}>", commentMessage.getPostId(), commentMessage.getCommentId(), commentMessage.getComments());
        String key = createKey(commentMessage.getPostId());
        List<CommentMessage> allComments = findAllComments(commentMessage.getPostId());
        allComments.add(commentMessage);
        hashOperations.put(COMMENT_KEY, key, allComments);
    }

    public void saveAllComments(Long postId, List<CommentMessage> allComments) {
        if (allComments == null || allComments.isEmpty()) {
            log.warn("allComments is empty");
            return;
        }
        log.debug("saveAllComments, postId:<{}>, commentsSize:", postId, allComments.size());
        String key = createKey(postId);
        hashOperations.put(COMMENT_KEY, key, allComments);
    }

    public void updateComment(CommentMessage commentMessage) {
        saveComment(commentMessage);
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    private String createKey(Long postId) {
        return KEY_PREFIX + ":" + postId;
    }

}
