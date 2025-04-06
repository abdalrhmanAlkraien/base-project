package com.alrjhi;

import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
@Log4j2
public class ReportApplication {

    public static void main(String[] args) throws JRException {

		String sourceFile = "/src/main/resources/reports/report.jrxml";

// Load the .jrxml file from the resources folder

		SpringApplication.run(ReportApplication.class, args);
    }

}
