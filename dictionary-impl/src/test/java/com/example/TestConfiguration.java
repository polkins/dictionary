package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import ru.nonsense.auth.client.feign.AuthControllerFeign;

@Profile("test")
@SpringBootConfiguration
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(value = {"com.example.dictionary"})
public class TestConfiguration {
    @Autowired
    protected TestRestTemplate restTemplate;

    @MockBean
    private AuthControllerFeign authControllerFeign;
}
