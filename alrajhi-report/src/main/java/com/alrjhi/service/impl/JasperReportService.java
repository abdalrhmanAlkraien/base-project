package com.alrjhi.service.impl;

import com.alrjhi.ReportApplication;
import com.alrjhi.service.ReportService;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;

@Service
@Log4j2
public class JasperReportService implements ReportService {

    @Override
    public byte[] generateReport() throws JRException {

        InputStream reportStream = ReportApplication.class
                .getClassLoader()
                .getResourceAsStream("reports/report.jrxml");

        if (reportStream == null) {
            throw new JRException("Report file not found in classpath: reports/report.jrxml");
        }

        JasperReport report = JasperCompileManager.compileReport(reportStream);
        log.info(report.getName());

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                report,
                new HashMap<>(), // No parameters
                new JREmptyDataSource() // Empty data source
        );

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }
}
