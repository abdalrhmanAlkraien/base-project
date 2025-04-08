package com.alrjhi.model;

import com.alrajhi.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/8/2025
 * @Time: 1:45 PM
 */
@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportEntity extends AbstractEntity {

    @Column(name = "customer_name", nullable = false)
    private String customerName;

}
