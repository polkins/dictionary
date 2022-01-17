package com.example.dictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.nonsense.auth.client.config.EnableSecurityAuthClient;

@SpringBootApplication
@EnableFeignClients(basePackages = {"ru.nonsense.auth.client.feign"})
@EnableSecurityAuthClient
public class DictionaryImplApplication {
    public static void main(String[] args) {
        SpringApplication.run(DictionaryImplApplication.class, args);
    }
}
