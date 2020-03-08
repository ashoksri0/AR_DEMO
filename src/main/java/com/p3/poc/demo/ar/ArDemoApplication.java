package com.p3.poc.demo.ar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
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

   /* @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Patient-360")
                        .description("patient-360 open-api docs")
                        .version("v0.0.1")
                        .license(new License().name("Platform3Solutions 1.0").url("http://localhost.org")));
                *//*.externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));*//*
    }*/

}
