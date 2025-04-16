package com.alrjhi;

import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.alrajhi"})
@Log4j2
public class ReportApplication {

    public static void main(String[] args) throws JRException {

        SpringApplication.run(ReportApplication.class, args);
    }

}
