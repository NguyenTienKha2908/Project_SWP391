package com.example.CustomerView;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.CustomerView.repository")
@EntityScan(basePackages = "com.example.CustomerView.entity")
public class CustomerViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerViewApplication.class, args);
    }
}
