package com.alrjhi.controller;

import com.alrajhi.constant.EndPoints;
import com.alrjhi.dto.request.AuthorRequest;
import com.alrjhi.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

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

    @Operation(summary = "create a new API to generate report")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/pdf",
                            schema = @Schema(implementation = AuthorRequest.class)
                    )
            }, description = "Generate the report"),

            @ApiResponse(responseCode = "400", content = {
                    @Content
            }, description = "Bad request"),

            @ApiResponse(responseCode = "404", content = {
                    @Content
            }, description = "Not Found")
    })
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
