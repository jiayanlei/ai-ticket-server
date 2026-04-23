package com.aiticket.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.aiticket.server.**.mapper")
@SpringBootApplication
public class AiTicketServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiTicketServerApplication.class, args);
    }
}
