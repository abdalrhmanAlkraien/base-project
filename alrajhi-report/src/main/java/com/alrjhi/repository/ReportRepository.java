package com.alrjhi.repository;

import com.alrajhi.repository.ApplicationRepository;
import com.alrjhi.model.ReportEntity;
import org.springframework.stereotype.Repository;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 4/8/2025
 * @Time: 1:54 PM
 */
@Repository
public interface ReportRepository extends ApplicationRepository<ReportEntity, Long> {
}
