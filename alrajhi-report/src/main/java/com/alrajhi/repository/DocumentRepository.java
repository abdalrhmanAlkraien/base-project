package com.alrajhi.repository;

import com.alrajhi.model.DocumentEntity;
import org.springframework.stereotype.Repository;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/8/2025
 * @Time: 1:54 PM
 */
@Repository
public interface DocumentRepository extends ApplicationRepository<DocumentEntity, Long> {
}
