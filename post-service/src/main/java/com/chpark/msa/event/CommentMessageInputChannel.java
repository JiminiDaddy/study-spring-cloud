package com.chpark.msa.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 1:53 PM
 */

// Comment-Service로부터 메시지를 받기 위한 Custom-Channel, 기본포맷은 Sink-class
public interface CommentMessageInputChannel {
    @Input("inboundComment")
    SubscribableChannel input();

}
