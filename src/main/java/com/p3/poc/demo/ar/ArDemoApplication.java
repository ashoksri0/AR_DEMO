package com.p3.poc.demo.ar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.p3.*.**")
@EnableTransactionManagement
public class ArDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArDemoApplication.class, args);
    }

}
