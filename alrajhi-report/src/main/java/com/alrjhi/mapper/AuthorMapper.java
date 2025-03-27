package com.alrjhi.mapper;

import com.alrjhi.dto.request.AuthorRequest;
import com.alrjhi.dto.response.AuthorResponse;
import com.alrjhi.model.Author;
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
