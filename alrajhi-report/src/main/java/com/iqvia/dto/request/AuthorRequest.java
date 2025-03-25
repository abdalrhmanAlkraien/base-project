package com.iqvia.dto.request;

import com.iqvia.model.enumeration.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Gender gender;
}
