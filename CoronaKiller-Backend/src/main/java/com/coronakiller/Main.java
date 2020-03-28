package com.coronakiller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Needed for enabling the SpringBoot's JPA Auditing
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}