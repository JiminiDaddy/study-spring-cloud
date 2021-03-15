package com.chpark.msa.event.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 2:23 PM
 */

public interface CommentMessageOutputChannel {
   @Output("outboundComment")
   MessageChannel output();
}
