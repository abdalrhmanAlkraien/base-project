package com.iqvia.service;

import com.iqvia.dto.request.AuthorRequest;
import com.iqvia.dto.response.AuthorResponse;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:47 PM
 */
public interface AuthorService {

    Page<AuthorResponse> getAllAuthor(final Map<String, String> filters);

    void createAuthor(final AuthorRequest authorRequest);

    void updateNumberOfBook(final Long authorId);
}
