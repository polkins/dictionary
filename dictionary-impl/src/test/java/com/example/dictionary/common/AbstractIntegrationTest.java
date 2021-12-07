package com.example.dictionary.common;

import com.example.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.BeforeMethod;

@ActiveProfiles("test")
@SpringBootTest(
        classes = TestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIntegrationTest extends PostgresIntegrationTest {
    @BeforeMethod
    public void setUp() {
    }
}
