package br.com.safework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SafeWorkWebAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(SafeWorkWebAppApplication.class, args);
    }
}
