package com.alrjhi.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/15/2025
 * @Time: 11:04 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndebtednessTable {

    private List<IndebtednessRow> rows;
}
