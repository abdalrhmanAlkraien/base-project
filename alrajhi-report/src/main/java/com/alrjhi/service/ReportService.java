package com.alrjhi.service;

import com.alrjhi.dto.request.ReportRequest;
import com.alrjhi.dto.response.ReportResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.jasperreports.engine.JRException;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:47 PM
 */
public interface ReportService {
    ReportResponse generateReport(final ReportRequest report) throws JRException, JsonProcessingException;
}
