package com.alrjhi.service.impl;

import com.alrjhi.ReportApplication;
import com.alrjhi.dto.request.ReportRequest;
import com.alrjhi.service.ReportService;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class JasperReportService implements ReportService {

    /**
     * To generate report
     *
     * @param report provide us the report information from the digital channel.
     * @return will return the report as a list of byte.
     * @throws JRException to throw Jasper exception.
     */
    @Override
    public byte[] generateReport(final ReportRequest reportRequest) throws JRException {

        InputStream reportStream = ReportApplication.class
                .getClassLoader()
                .getResourceAsStream("reports/report.jrxml");

        if (reportStream == null) {
            throw new JRException("Report file not found in classpath: reports/report.jrxml");
        }

        JasperReport report = JasperCompileManager.compileReport(reportStream);
        log.info(report.getName());

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("currentDate", new Date());
        parameters.put("refNo", reportRequest.getReferenceNumber());
        parameters.put("customerName", reportRequest.getCustomerName());
        parameters.put("identityNo", reportRequest.getIdentityNumber());
        parameters.put("iban", reportRequest.getIban());
        parameters.put("contractNo", reportRequest.getContractNumber());
        parameters.put("productType", reportRequest.getProductType());
        parameters.put("totalAmount", reportRequest.getTotalAmount());

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                report,
                parameters,
                new JREmptyDataSource() // Empty data source
        );

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }
}
