package com.example.volunteer_platform.client;

import com.example.volunteer_platform.client.console_ui.BaseMenuClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientApplication.class, args);

        BaseMenuClient baseMenuClient = context.getBean(BaseMenuClient.class);

        baseMenuClient.start();
    }
}
