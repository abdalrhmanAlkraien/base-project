package com.alrjhi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 7/4/2025
 * @Time: 10:49 AM
 */
@Data
@AllArgsConstructor
@Builder
public class ReportResponse {

    private Long id;
    private String name;
    private Integer numberOfBook;
}
