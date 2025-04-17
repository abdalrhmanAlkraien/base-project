package com.alrajhi.advice;

import com.alrajhi.error.error.ApiCallError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/17/2025
 * @Time: 12:17 PM
 */
@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        int status = 0;
        if (response instanceof ServletServerHttpResponse servletResponse) {

            HttpServletResponse res = servletResponse.getServletResponse();
            status = res.getStatus();
        }

        if(status < 200 || status > 300) {

            ApiCallError callError = mapper.convertValue(body, ApiCallError.class);

            return ErrorResponseWrapper
                    .builder()
                    .success(false)
                    .requestPath(request.getURI().getPath())
                    .status(status)
                    .httpMethod(request.getMethod().name())
                    .errorMessage(!Objects.isNull(callError.details()) ? callError.details() : null)
                    .errorCode(callError.errorCode())
                    .build();
            // wrong response

        } else {

            // success Wrapper
            return SuccessResponseWrapper
                    .builder()
                    .success(true)
                    .message("Success")
                    .requestPath(request.getURI().getPath())
                    .status(status)
                    .httpMethod(request.getMethod().name())
                    .data(body)
                    .build();
        }
    }
}
