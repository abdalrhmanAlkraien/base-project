package com.alrajhi;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.alrajhi", "com.alrajhi.config"})
@Log4j2
public class ReportApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReportApplication.class, args);
    }
}
