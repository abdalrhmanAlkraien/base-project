package com.alrajhi.service.impl;

import com.alrajhi.dto.request.ReportRequest;
import com.alrajhi.dto.response.ReportResponse;
import com.alrajhi.error.error.BusinessErrorCodes;
import com.alrajhi.error.exception.BusinessRoleException;
import com.alrajhi.mapper.AuthorMapper;
import com.alrajhi.model.Author;
import com.alrajhi.repository.AuthorRepository;
import com.alrajhi.service.AuthorService;
import com.alrajhi.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:48 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Page<ReportResponse> getAllAuthor(Map<String, String> filters) {

        return authorRepository.findAll(WebUtils.buildPageable(filters))
                .map(authorMapper::toDto);
    }

    @Override
    public void createAuthor(ReportRequest reportRequest) {

        authorRepository.save(authorMapper.toEntity(reportRequest));
    }

    @Override
    public void updateNumberOfBook(Long authorId) {

        Author author = getAuthorById(authorId);

        author.setNumberOfBook(author.getNumberOfBook() + 1);

        authorRepository.save(author);
    }

    private Author getAuthorById(final Long authorId) {

        return authorRepository.findById(authorId)
                .orElseThrow(()-> new BusinessRoleException(BusinessErrorCodes.NO_RECORD_FOUND));
    }
}
