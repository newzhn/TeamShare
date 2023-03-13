package com.zhn.teamsharebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * author zhn
 */
@SpringBootApplication
@MapperScan("com.zhn.teamsharebackend.mapper")
public class TeamShareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamShareBackendApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
