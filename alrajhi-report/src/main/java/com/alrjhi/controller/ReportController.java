package com.alrjhi.controller;

import com.alrajhi.constant.EndPoints;
import com.alrajhi.dto.request.DocumentRequest;
import com.alrajhi.util.GenericResponse;
import com.alrjhi.dto.response.DocumentResponse;
import com.alrjhi.service.DocumentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 11:34 PM
 */
@RestController
@RequestMapping(EndPoints.DOCUMENT)
@RequiredArgsConstructor
@Log4j2
public class ReportController {

    private final DocumentService documentService;

    @Operation(summary = "create a new API to generate report")
    @ApiResponses(value = {

            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DocumentRequest.class)
                    )
            }, description = "Generate the document"),

            @ApiResponse(responseCode = "400", content = {
                    @Content
            }, description = "Bad request"),

            @ApiResponse(responseCode = "404", content = {
                    @Content
            }, description = "Not Found")
    })
    @PostMapping
    public ResponseEntity<GenericResponse<DocumentResponse>> generateDocument(
            @RequestBody @Valid DocumentRequest report
    ) throws JRException, JsonProcessingException, MalformedURLException {

        return ResponseEntity
                .ok()
                .body(GenericResponse.success(documentService.generateReport(report)));
    }
}
