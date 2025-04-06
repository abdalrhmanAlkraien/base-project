package com.alrjhi.service;

import net.sf.jasperreports.engine.JRException;
import org.springframework.core.io.ByteArrayResource;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:47 PM
 */
public interface ReportService {
    byte[] generateReport() throws JRException;
}
