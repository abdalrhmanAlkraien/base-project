package com.alrajhi.dto.request;

import com.alrajhi.model.enumerate.Language;
import com.alrajhi.model.enumerate.LetterType;
import com.alrajhi.model.enumerate.ResponseType;
import com.alrajhi.model.enumerate.Source;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 3:30 PM
 */
@Data
@AllArgsConstructor
@NotNull
public class DocumentRequest {


    private Map<String, Object> params;

    @NotNull
    private String cicNum;

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
