package com.example.dictionary.common;

import com.example.TestConfiguration;
import com.example.dictionary.controller.DictionaryBankController;
import com.example.dictionary.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.BeforeMethod;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@ActiveProfiles("test")
@SpringBootTest(
        classes = TestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIntegrationTest extends PostgresIntegrationTest {
    @Autowired
    protected DictionaryBankController dictionaryBankController;

    @Autowired
    protected BankService bankService;

    @BeforeMethod
    public void setUp() {
    }

    protected static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();

        return headers;
    }
}
