package com.alrjhi.service.impl;

import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.alrjhi.ReportApplication;
import com.alrjhi.dto.request.DocumentRequest;
import com.alrjhi.dto.response.DocumentResponse;
import com.alrjhi.model.Language;
import com.alrjhi.model.ReportEntity;
import com.alrjhi.model.ResponseType;
import com.alrjhi.repository.ReportRepository;
import com.alrjhi.service.DocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class JasperDocumentService implements DocumentService {

    private final ReportRepository reportRepository;
    private final ObjectMapper mapper;

    /**
     * To generate document
     *
     * @param documentRequest provide us the document information from the digital channel.
     * @return will return ReportResponse.
     * @throws JRException to throw Jasper exception.
     */
    @Override
    public DocumentResponse generateReport(final DocumentRequest documentRequest) throws JRException, JsonProcessingException {

        Map<String, Object> parameters = new HashMap<>();
        JasperReport report = null;
        InputStream reportStream = null;
        switch (documentRequest.getLetterType()) {

            case INDEBTEDNESS_LETTER:

                if (documentRequest.getReportLanguage().equals(Language.AR)) {
                    reportStream = ReportApplication.class
                            .getClassLoader()
                            .getResourceAsStream("reports/reportAr.jrxml");
                } else {
                    reportStream = ReportApplication.class
                            .getClassLoader()
                            .getResourceAsStream("reports/reportEn.jrxml");
                }

                if (reportStream == null) {
                    throw new BusinessRoleException(BusinessErrorCodes.REPORT_IS_NULL);
                }

                report = JasperCompileManager.compileReport(reportStream);

                if (documentRequest.getParams().isEmpty()) {

                    throw new BusinessRoleException(BusinessErrorCodes.PARAMS_MISMATCH);
                }

                parameters.put("currentDate", new Date());

                if (!documentRequest.getParams().containsKey("tables")) {
                    throw new BusinessRoleException(BusinessErrorCodes.REPORT_IS_NULL);
                }

                List<Map<String, Map<String, String>>> tables = mapper.convertValue(
                        documentRequest.getParams().get("tables"),
                        new TypeReference<List<Map<String, Map<String, String>>>>() {
                        }
                );


                if (tables.isEmpty()) {
                    throw new BusinessRoleException(BusinessErrorCodes.REPORT_IS_NULL);
                }
                Map<Integer, Map<String, Map<String,String>>> dataSource = new HashMap<>();

                IntStream.range(0, tables.size())
                        .forEach(i -> {

                            Map<String, Map<String, String>> row = tables.get(i);

                            log.info("collect data of table {}", i);

                            if (i == 0) {

                                if (!row.isEmpty()) {

                                    row.forEach((k, v) -> {

                                        Map<String, Map<String, String>> tempMap = dataSource.get(i); //original

                                        if(dataSource.containsKey(i)) {
                                            tempMap = dataSource.get(i);
                                            tempMap.put(k,new HashMap<>(v));
                                            dataSource.put(i, tempMap);
                                        } else {
                                            dataSource.put(i, new HashMap<>(Map.of(k,v)));
                                        }

                                    });

                                }

                            } else if (i == 1) {
                                // If there is any other tables
                            }
                        });

                parameters.put("refNo", documentRequest.getParams().get("referenceNumber"));
                parameters.put("customerName", documentRequest.getParams().get("customerName"));
                parameters.put("identityNo", documentRequest.getParams().get("identityNumber"));

                Collection<Map<String, ?>> table1DataSource = new ArrayList<>();

                dataSource.get(0).forEach((k, v) -> {
                    table1DataSource.add(v);
                });


                JRDataSource tableDataSource = new JRBeanCollectionDataSource(table1DataSource);
                parameters.put("tableDataSource", tableDataSource);

                break;

            default:
                log.error("Invalid report letter type: {}", documentRequest.getLetterType());
                throw new BusinessRoleException(BusinessErrorCodes.WRONG_LETTER_TYPE);
        }

        if (Objects.isNull(report)) {
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
                        .data(mapper.writeValueAsString(documentRequest))
                        .source((documentRequest.getSource()))
                        .letterType(documentRequest.getLetterType())
                        .responseType(documentRequest.getResponseType())
                        .build());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Encode to Base64
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        if (documentRequest.getResponseType().equals(ResponseType.FILE_ID)) {

            // call EBS
            try {
                Thread.sleep(3000); // sleep for 3000 milliseconds = 3 seconds
            } catch (InterruptedException e) {
                log.error(e);
            }

            entity.setReferenceId(UUID.randomUUID().toString());
            reportRepository.saveAndFlush(entity);

            return DocumentResponse
                    .builder()
                    .id(entity.getId())
                    .file(base64)
                    .source(documentRequest.getSource())
                    .letterType(documentRequest.getLetterType())
                    .reportLanguage(documentRequest.getReportLanguage())
                    .build();
        } else {

            return DocumentResponse
                    .builder()
                    .id(entity.getId())
                    .file(base64)
                    .source(documentRequest.getSource())
                    .letterType(documentRequest.getLetterType())
                    .reportLanguage(documentRequest.getReportLanguage())
                    .build();
        }


    }
}
