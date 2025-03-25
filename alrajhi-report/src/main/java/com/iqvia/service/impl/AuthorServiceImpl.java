package com.iqvia.service.impl;

import com.iqvia.dto.request.AuthorRequest;
import com.iqvia.dto.response.AuthorResponse;
import com.iqvia.error.error.BusinessErrorCodes;
import com.iqvia.error.exception.BusinessRoleException;
import com.iqvia.mapper.AuthorMapper;
import com.iqvia.model.Author;
import com.iqvia.repository.AuthorRepository;
import com.iqvia.service.AuthorService;
import com.iqvia.util.WebUtils;
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
    public Page<AuthorResponse> getAllAuthor(Map<String, String> filters) {

        return authorRepository.findAll(WebUtils.buildPageable(filters))
                .map(authorMapper::toDto);
    }

    @Override
    public void createAuthor(AuthorRequest authorRequest) {

        authorRepository.save(authorMapper.toEntity(authorRequest));
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
