package com.example.volunteer_platform;

import com.example.volunteer_platform.user_interface.BaseMenuUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VolunteerPlatformApplication implements CommandLineRunner {

    private final BaseMenuUI baseMenuUI;

    @Autowired
    public VolunteerPlatformApplication(BaseMenuUI baseMenuUI) {
        this.baseMenuUI = baseMenuUI;
    }

    public static void main(String[] args) {
        SpringApplication.run(VolunteerPlatformApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        baseMenuUI.start();
    }
}
