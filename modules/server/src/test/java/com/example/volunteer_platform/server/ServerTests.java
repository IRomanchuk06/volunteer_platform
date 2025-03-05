package com.example.volunteer_platform.server;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Tag("contextTest")
public class ServerTests {
    @Test
    public void contextLoads() {
    }
}
