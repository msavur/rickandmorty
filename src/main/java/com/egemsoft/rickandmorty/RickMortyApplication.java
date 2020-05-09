package com.egemsoft.rickandmorty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.egemsoft"})
@EntityScan(basePackages = {"com.egemsoft"})
@EnableJpaRepositories(basePackages = "com.egemsoft")
public class RickMortyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickMortyApplication.class, args);
    }
}
