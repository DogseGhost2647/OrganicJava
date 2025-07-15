package com.example.organic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.organic"})
public class OrganicApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrganicApplication.class, args);
    }
}
