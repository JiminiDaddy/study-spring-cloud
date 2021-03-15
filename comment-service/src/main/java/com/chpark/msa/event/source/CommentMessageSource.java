package com.chpark.msa.event.source;

import com.chpark.msa.domain.Comment;
import com.chpark.msa.event.channel.CommentMessageOutputChannel;
import com.chpark.msa.event.model.CommentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/12
 * Time : 12:19 PM
 */

@Slf4j
// Spring-cloud-stream에 CommentMessageOutputChannel을 Message-broker로 바인딩한다.
@EnableBinding(CommentMessageOutputChannel.class)
@RequiredArgsConstructor
@Component
public class CommentMessageSource {
    private final CommentMessageOutputChannel outputChannel;

    // 새로운 코멘트가 작성될경우, 메시지를 발행한다.
    public void publishNewCommentSave(String action, Comment comment) {
        log.debug("Sending kafka message <{}> for postId:<{}>, commentId:<{}>",
                action, comment.getCommentId().getPostId(), comment.getCommentId().getId());

        CommentMessage savedComment = CommentMessage.builder().action("SAVE").comment(comment).build();

        // 채널을 이용해 Message-broker와 통신하게 된다. 채널은 Message-queue 위에 존재한다.
        outputChannel.output()
                .send(MessageBuilder.withPayload(savedComment).build());
    }

}
