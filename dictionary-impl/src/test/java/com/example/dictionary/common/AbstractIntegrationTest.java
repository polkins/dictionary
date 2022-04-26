package com.example.dictionary.common;

import com.example.TestConfiguration;
import com.example.dictionary.controller.DictionaryBankController;
import com.example.dictionary.mapper.EmployeeMapper;
import com.example.dictionary.mapper.qualifier.EmployeeHandler;
import com.example.dictionary.repository.CarRepository;
import com.example.dictionary.repository.EngineRepository;
import com.example.dictionary.service.BankService;
import com.example.dictionary.service.MyJDBCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.testng.annotations.BeforeMethod;
import ru.nonsense.auth.client.feign.AuthControllerFeign;

@ActiveProfiles("test")
@SpringBootTest(
        classes = TestConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class AbstractIntegrationTest extends PostgresIntegrationTest {
    @Autowired
    private AuthControllerFeign authControllerFeign;

    @Autowired
    protected DictionaryBankController dictionaryBankController;

    @Autowired
    protected BankService bankService;

    @Autowired
    protected MyJDBCService myJDBCService;

    @Autowired
    protected EmployeeMapper employeeMapper;

    @Autowired
    protected EmployeeHandler employeeHandler;

    @Autowired
    protected EngineRepository engineRepository;

    @Autowired
    protected CarRepository carRepository;

    @BeforeMethod
    public void setUp() {
    }

    protected static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "auth");

        return headers;
    }
}
