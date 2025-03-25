package com.iqvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:45 PM
 */
@NoRepositoryBean
public interface ApplicationRepository<T, I> extends JpaSpecificationExecutor<T>, JpaRepository<T, I>  {
}
