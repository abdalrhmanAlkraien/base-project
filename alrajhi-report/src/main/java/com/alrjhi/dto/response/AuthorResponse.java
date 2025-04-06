package com.alrjhi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:49 PM
 */
@Data
@AllArgsConstructor
@Builder
public class AuthorResponse {

    private Long id;
    private String name;
    private Integer numberOfBook;
}
