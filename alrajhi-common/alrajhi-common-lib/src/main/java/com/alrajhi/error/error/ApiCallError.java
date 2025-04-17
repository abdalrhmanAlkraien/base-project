package com.alrajhi.error.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/17/2025
 * @Time: 12:48 PM
 */
@JsonRootName("ApiError")
public record ApiCallError<T>(
        @JsonProperty("errorCode") ErrorCategories errorCode,
        @JsonProperty("url") String url,
        @SuppressWarnings("unchecked")
        @JsonProperty("details") List<T> details) {
}
