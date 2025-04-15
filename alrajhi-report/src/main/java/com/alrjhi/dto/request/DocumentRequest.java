package com.alrjhi.dto.request;

import com.alrjhi.model.Language;
import com.alrjhi.model.LetterType;
import com.alrjhi.model.ResponseType;
import com.alrjhi.model.Source;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 7/4/2025
 * @Time: 10:49 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {

    private Map<String, Object> params;

    @NotNull
    private String requestId;

    @NotNull
    private ResponseType responseType;

    @NotNull
    private Source source;

    @NotNull
    private LetterType letterType;
    @NotNull
    private Language reportLanguage;
}
