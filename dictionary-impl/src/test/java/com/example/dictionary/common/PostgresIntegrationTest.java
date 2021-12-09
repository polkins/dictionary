package com.example.dictionary.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;
import org.testcontainers.utility.MountableFile;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@ContextConfiguration(initializers = PostgresIntegrationTest.Initializer.class)
public abstract class PostgresIntegrationTest extends AbstractTestNGSpringContextTests {

    private static final Consumer<OutputFrame> logConsumer = out ->
            LoggerFactory.getLogger(PostgresIntegrationTest.class).debug(StringUtils.stripEnd(out.getUtf8String(), "\n"));

    protected static GenericContainer<?> postgres = new GenericContainer<>("postgres:14.1")
            .withAccessToHost(true)
            .withExposedPorts(5432)
            .withEnv("POSTGRES_DB", "postgres")
            .withEnv("POSTGRES_USER", "postgres")
            .withEnv("POSTGRES_PASSWORD", "admin")
            .withCopyFileToContainer(file("docker/dev/1-init.sql"), "/docker-entrypoint-initdb.d/1-init.sql")
            .withLogConsumer(logConsumer)
            .withTmpFs(Collections.singletonMap("/var/lib/postgresql/data", "rw"));

    private static boolean postgresEnabled() {
        return !Boolean.parseBoolean(System.getenv("POSTGRES_LOCAL"));
    }

    /**
     * Programmatic initialization of the <code>application context</code>.
     */
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues
                    .of(springApplicationProperties())
                    .applyTo(applicationContext.getEnvironment(), TestPropertyValues.Type.SYSTEM_ENVIRONMENT, "test");
        }

        private static String[] springApplicationProperties() {
            String postgresLocation = System.getProperty("postgres.location");
            String jdbcUrl;

            if (!postgresEnabled()) {
                return new String[0];
            }

            if (StringUtils.equals(postgresLocation, "local")) {
                jdbcUrl =
                        String.format("jdbc:postgresql://%s:%d/postgres?currentSchema=dictionary", "localhost", 5432);

            } else {
                postgres.start();
                log.info("Postgres container started. Address: {}, port: {}", postgres.getContainerIpAddress(), postgres.getFirstMappedPort());
                jdbcUrl = String.format("jdbc:postgresql://%s:%d/postgres?currentSchema=dictionary,public",
                        postgres.getContainerIpAddress(), postgres.getFirstMappedPort());
            }

            return new String[]{
                    // DB users from "init.sql"
                    "spring.datasource.url=" + jdbcUrl,
                    "spring.datasource.username=dictionary",
                    "spring.datasource.password=dictionary",
                    "spring.liquibase.url=" + jdbcUrl,
                    "spring.liquibase.user=dictionary",
                    "spring.liquibase.password=dictionary"
            };
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static MountableFile file(String fileName) {
        ClassLoader classLoader = AbstractIntegrationTest.class.getClassLoader();
        URL resource = Objects.requireNonNull(classLoader.getResource("."));
        try {
            Path testClassesDir = Paths.get(resource.toURI());
            Path path = testClassesDir.resolve("../../../").resolve(fileName);
            Files.createDirectories(testClassesDir.resolve(fileName).getParent());
            Files.copy(path, testClassesDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return MountableFile.forClasspathResource(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
