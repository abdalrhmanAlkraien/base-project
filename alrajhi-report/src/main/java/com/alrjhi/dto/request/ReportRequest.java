package com.alrjhi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 7/4/2025
 * @Time: 10:49 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRequest {

    @NotBlank
    private String customerName;
    @NotBlank
    private String referenceNumber;
    @NotBlank
    private String identityNumber;
    @NotBlank
    private String iban;
    @NotBlank
    private String contractNumber;
    @NotBlank
    private String productType;
    @NotBlank
    private String totalAmount;
}
