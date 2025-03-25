package com.iqvia.controller;

import com.iqvia.constant.EndPoints;
import com.iqvia.dto.request.AuthorRequest;
import com.iqvia.dto.response.AuthorResponse;
import com.iqvia.service.AuthorService;
import com.iqvia.util.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
@RequestMapping(EndPoints.AUTHOR_PATH)
@RequiredArgsConstructor
@Log4j2
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<GenericResponse<Page<AuthorResponse>>> getAllAuthor(
            @RequestParam final Map<String, String> filters
    ) {

        log.info("call get all author API");
        return ResponseEntity.ok(GenericResponse.success(authorService.getAllAuthor(filters)));
    }

    @PostMapping
    public ResponseEntity<GenericResponse<String>> createNewAuthor(
            @RequestBody @Valid final AuthorRequest authorRequest
    ) throws URISyntaxException {

        log.info("call create new Author API");
        authorService.createAuthor(authorRequest);
        return ResponseEntity.created(new URI(EndPoints.AUTHOR_PATH)).body(GenericResponse.success("Author has been created"));
    }

    @PutMapping(EndPoints.ID_KEY)
    public ResponseEntity<GenericResponse<String>> updateNumberOfBook(
            @PathVariable("id") final Long authorId
    ) {
        authorService.updateNumberOfBook(authorId);
        return ResponseEntity.ok(GenericResponse.success("the Author has been updated"));
    }
}
