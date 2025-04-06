package com.alrajhi.service;

import com.alrajhi.dto.request.ReportRequest;
import com.alrajhi.dto.response.ReportResponse;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:47 PM
 */
public interface AuthorService {

    Page<ReportResponse> getAllAuthor(final Map<String, String> filters);

    void createAuthor(final ReportRequest reportRequest);

    void updateNumberOfBook(final Long authorId);
}
