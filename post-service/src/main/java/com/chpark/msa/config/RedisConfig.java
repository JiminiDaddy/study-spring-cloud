package com.chpark.msa.config;

import com.chpark.msa.event.model.CommentMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 3:41 PM
 */

@Configuration
public class RedisConfig {
    // Redis-server에 실제 Database-Connection을 설정한다.
    // TODO Lettuce가 Jedis에 비해 성능이 좋다고 하니 변경해서도 구현해보기!
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        /* deprecated
        factory.setHostName(ser);
        factory.setPort();
        */
        return factory;
    }

    // Reids-server에 작업을 수행하는데 필요한 RedisTemplate 객체 생성?
    @Bean
    public RedisTemplate<String, List<CommentMessage>> redisTemplate() {
        RedisTemplate<String, List<CommentMessage>> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
