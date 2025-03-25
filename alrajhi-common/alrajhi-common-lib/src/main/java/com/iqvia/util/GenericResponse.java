package com.iqvia.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/4/2024
 * @Time: 11:51 PM
 */
@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {

    private  Boolean success;
    private  String message;
    private T data;

    public static <T> GenericResponse<T> empty(){
        return GenericResponse.<T>builder()
                .success(false)
                .message("")
                .data(null)
                .build();
    }

    public static <T> GenericResponse<T> success(T data){
        return GenericResponse.<T>builder()
                .success(true)
                .message("success")
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(){
        return GenericResponse.<T>builder()
                .success(true)
                .message("success")
                .build();
    }

    public static <T> GenericResponse<T> error(T data){
        return GenericResponse.<T>builder()
                .success(false)
                .message("error")
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> success(T data, String message){
        return GenericResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> GenericResponse<T> error(T data, String message){
        return GenericResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
