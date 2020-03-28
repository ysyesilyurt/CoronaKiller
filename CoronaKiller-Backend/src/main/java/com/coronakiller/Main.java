package com.coronakiller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

/**
 * TODO:
 *  - Finish Impl of Cont-Service-Repo => ALPER + YAVUZ
 *  - Security => YAVUZ
 *  - Unit Testing + PROD DB PREPARATION => ALPER
 *  - Swagger + README + DB DESIGN => YAVUZ
 *  - Postman Collections => YAVUZ + ALPER
 */