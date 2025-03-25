package com.iqvia.repository;

import com.iqvia.model.Author;
import org.springframework.stereotype.Repository;

/**
 * @author: Abd-alrhman Alkraien.
 * @Date: 11/3/2024
 * @Time: 10:46 PM
 */
@Repository
public interface AuthorRepository extends ApplicationRepository<Author, Long> {
}
