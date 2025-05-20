package com.user.imvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ImvsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImvsApplication.class, args);
    }

}
