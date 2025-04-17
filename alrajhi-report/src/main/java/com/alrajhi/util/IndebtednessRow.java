package com.alrajhi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 11:05 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndebtednessRow {

    private String contractNo;
    private String productType;
    private String totalAmount;
}
