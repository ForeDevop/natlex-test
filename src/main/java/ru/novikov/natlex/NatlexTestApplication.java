package ru.novikov.natlex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class NatlexTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NatlexTestApplication.class, args);
    }

}
