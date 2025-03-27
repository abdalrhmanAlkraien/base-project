package com.alrjhi.controller;

import com.alrjhi.constant.EndPoints;
import com.alrjhi.dto.request.AuthorRequest;
import com.alrjhi.dto.response.AuthorResponse;
import com.alrjhi.service.ReportService;
import com.alrjhi.util.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 11:34 PM
 */
@RestController
@RequestMapping(EndPoints.REPORT)
@RequiredArgsConstructor
@Log4j2
public class ReportController {

    private final ReportService reportService;
    @GetMapping
    public ResponseEntity<ByteArrayResource> getReport() throws URISyntaxException, JRException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simple_report.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new ByteArrayResource(reportService.generateReport()));
    }
}
