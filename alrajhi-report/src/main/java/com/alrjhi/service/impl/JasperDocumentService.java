package com.alrjhi.service.impl;

import com.alrajhi.client.FileClient;
import com.alrajhi.dto.request.DocumentRequest;
import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.alrajhi.model.enumerate.Language;
import com.alrajhi.model.enumerate.ResponseType;
import com.alrjhi.ReportApplication;
import com.alrjhi.dto.response.DocumentResponse;
import com.alrjhi.model.DocumentEntity;
import com.alrjhi.repository.DocumentRepository;
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
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class JasperDocumentService implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ObjectMapper mapper;
    private final FileClient fileClient;

    /**
     * To generate document
     *
     * @param documentRequest provide us the document information from the digital channel.
     * @return will return ReportResponse.
     * @throws JRException to throw Jasper exception.
     */
    @Override
    public DocumentResponse generateReport(final DocumentRequest documentRequest) throws JRException, JsonProcessingException, MalformedURLException {

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

                List<List<Map<String, String>>> tables = mapper.convertValue(
                        ((List<Map<String, Object>>) documentRequest.getParams().get("tables"))
                                .stream()
                                .map(table -> table.get("rows"))
                                .collect(Collectors.toList()),
                        new TypeReference<List<List<Map<String, String>>>>() {
                        }
                );


                if (tables.isEmpty()) {
                    throw new BusinessRoleException(BusinessErrorCodes.REPORT_IS_NULL);
                }

                parameters.put("refNo", documentRequest.getParams().get("referenceNumber"));
                parameters.put("customerName", documentRequest.getParams().get("customerName"));
                parameters.put("identityNo", documentRequest.getParams().get("identityNumber"));

                Collection<Map<String, ?>> table1DataSource = new ArrayList<>();

                tables.get(0).forEach(table1DataSource::add);


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

        DocumentEntity entity = documentRepository.saveAndFlush(
                DocumentEntity
                        .builder()
                        .data(mapper.writeValueAsString(documentRequest))
                        .source((documentRequest.getSource()))
                        .letterType(documentRequest.getLetterType())
                        .responseType(documentRequest.getResponseType())
                        .requestId(documentRequest.getRequestId())
                        .build());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        // Encode to Base64
        String contentFile = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        if (documentRequest.getResponseType().equals(ResponseType.FILE_ID)) {

            // call EBS

            entity.setReferenceId(UUID.randomUUID().toString());
            fileClient.uploadFile(documentRequest, contentFile);
            documentRepository.saveAndFlush(entity);

            return DocumentResponse
                    .builder()
                    .id(entity.getId())
                    .file(contentFile)
                    .source(documentRequest.getSource())
                    .letterType(documentRequest.getLetterType())
                    .reportLanguage(documentRequest.getReportLanguage())
                    .build();
        } else {

            return DocumentResponse
                    .builder()
                    .id(entity.getId())
                    .file(contentFile)
                    .source(documentRequest.getSource())
                    .letterType(documentRequest.getLetterType())
                    .reportLanguage(documentRequest.getReportLanguage())
                    .build();
        }


    }
}
