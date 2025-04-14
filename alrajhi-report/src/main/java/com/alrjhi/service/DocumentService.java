package com.alrjhi.service;

import com.alrjhi.dto.request.DocumentRequest;
import com.alrjhi.dto.response.DocumentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.jasperreports.engine.JRException;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:47 PM
 */
public interface DocumentService {
    DocumentResponse generateReport(final DocumentRequest report) throws JRException, JsonProcessingException;
}
