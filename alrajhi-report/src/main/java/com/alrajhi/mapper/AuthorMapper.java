package com.alrajhi.mapper;

import com.alrajhi.dto.request.ReportRequest;
import com.alrajhi.dto.response.ReportResponse;
import com.alrajhi.model.Author;
import org.mapstruct.Mapper;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:52 PM
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(final ReportRequest reportRequest);

    ReportResponse toDto(final Author author);
}
