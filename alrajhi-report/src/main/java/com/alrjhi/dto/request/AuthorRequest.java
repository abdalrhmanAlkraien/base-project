package com.alrjhi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:49 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    @NotBlank
    private String name;
}
