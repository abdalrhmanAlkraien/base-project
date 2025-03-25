package com.iqvia.mapper;

import com.iqvia.dto.request.AuthorRequest;
import com.iqvia.dto.response.AuthorResponse;
import com.iqvia.model.Author;
import org.mapstruct.Mapper;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:52 PM
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(final AuthorRequest authorRequest);

    AuthorResponse toDto(final Author author);
}
