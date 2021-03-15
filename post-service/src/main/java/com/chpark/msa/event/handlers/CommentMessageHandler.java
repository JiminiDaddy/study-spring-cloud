package com.chpark.msa.event.handlers;

import com.chpark.msa.event.CommentMessageInputChannel;
import com.chpark.msa.event.model.CommentMessage;
import com.chpark.msa.repository.CommentRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 1:59 PM
 */

@Slf4j
@RequiredArgsConstructor
// Sink.class 대신 직접구현한 CommentMessageChannel을 사용한다.
@EnableBinding(CommentMessageInputChannel.class)
public class CommentMessageHandler {
    private final CommentRedisRepository commentRedisRepository;

    // 메시지를 수신할 때 마다 로그 기록
    // Spring-loud-stream은 채널에서 받은 메시지를 자동으로 역직렬화시킨다.
    @StreamListener("inboundComment")
    public void receiveCommentMessage(CommentMessage commentMessage) {
        log.debug("Received event[{}] : for postId:<{}>, commentId:<{}>, author:<{}>, comments:[{}]",
                commentMessage.getAction(), commentMessage.getPostId(), commentMessage.getCommentId(),
                commentMessage.getAuthor(), commentMessage.getComments());
        switch (commentMessage.getAction()) {
            case "SAVE":
                commentRedisRepository.saveComment(commentMessage);
                break;
            default:
                log.warn("Unknown action : <{}>", commentMessage.getAction());
                break;
        }
    }
}
