package com.example.volunteer_platform.server.integration;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractIntegrationTests {

    private static final MySQLContainer<?> mysqlContainer;

    static {
        mysqlContainer = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("testdb")
                .withUsername("testuser")
                .withPassword("testpass");
        mysqlContainer.start();
    }

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }
}
