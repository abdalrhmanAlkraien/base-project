package com.alrjhi.service.impl;

import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.alrjhi.ReportApplication;
import com.alrjhi.dto.request.ReportRequest;
import com.alrjhi.dto.response.ReportResponse;
import com.alrjhi.model.Language;
import com.alrjhi.model.ReportEntity;
import com.alrjhi.repository.ReportRepository;
import com.alrjhi.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class JasperReportService implements ReportService {

    private final ReportRepository reportRepository;
    private final ObjectMapper mapper;

    /**
     * To generate report
     *
     * @param report provide us the report information from the digital channel.
     * @return will return ReportResponse.
     * @throws JRException to throw Jasper exception.
     */
    @Override
    public ReportResponse generateReport(final ReportRequest reportRequest) throws JRException, JsonProcessingException {

        Map<String, Object> parameters = new HashMap<>();
        JasperReport report = null;
        InputStream reportStream = null;
        switch (reportRequest.getLetterType()) {

            case INDEBTEDNESS_LETTER:
                if(reportRequest.getReportLanguage().equals(Language.ARABIC)) {
                    reportStream = ReportApplication.class
                            .getClassLoader()
                            .getResourceAsStream("reports/reportAr.jrxml");
                } else {
                    reportStream = ReportApplication.class
                            .getClassLoader()
                            .getResourceAsStream("reports/reportEn.jrxml");
                }

                if (reportStream == null) {
                    throw new JRException("Report file not found in classpath: reports/report.jrxml");
                }

                report = JasperCompileManager.compileReport(reportStream);

                parameters.put("currentDate", new Date());
                parameters.put("refNo", reportRequest.getReferenceNumber());
                parameters.put("customerName", reportRequest.getCustomerName());
                parameters.put("identityNo", reportRequest.getIdentityNumber());
                parameters.put("iban", reportRequest.getIban());
                parameters.put("contractNo", reportRequest.getContractNumber());
                parameters.put("productType", reportRequest.getProductType());
                parameters.put("totalAmount", reportRequest.getTotalAmount());

                break;

            default:
                log.error("Invalid report letter type: {}", reportRequest.getLetterType());
                throw new BusinessRoleException(BusinessErrorCodes.WRONG_LETTER_TYPE);
        }

        if(Objects.isNull(report)) {
            throw new BusinessRoleException(BusinessErrorCodes.CANT_CALL_INTERNAL_API);
        }

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                report,
                parameters,
                new JREmptyDataSource() // Empty data source
        );

        ReportEntity entity = reportRepository.saveAndFlush(
                ReportEntity
                        .builder()
                        .data(mapper.writeValueAsString(reportRequest))
                        .source((reportRequest.getSource()))
                        .letterType(reportRequest.getLetterType())
                        .build());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Encode to Base64
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());


        return ReportResponse
                .builder()
                .id(entity.getId())
                .file(base64)
                .source(reportRequest.getSource())
                .letterType(reportRequest.getLetterType())
                .reportLanguage(reportRequest.getReportLanguage())
                .build();
    }
}
