package com.chpark.msa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Choen-hee Park
 * User : chpark
 * Date : 2021/03/15
 * Time : 5:13 PM
 */

@Configuration
@ComponentScan
public class ServerConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
