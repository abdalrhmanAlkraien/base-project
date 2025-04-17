package com.alrajhi.model;

import com.alrajhi.model.enumerate.Language;
import com.alrajhi.model.enumerate.LetterType;
import com.alrajhi.model.enumerate.ResponseType;
import com.alrajhi.model.enumerate.Source;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
public class DocumentEntity extends AbstractEntity {

    @Column(name = "data", columnDefinition = "JSON")
    private String data;

    @Column(name = "referance_id", unique = true)
    private String referenceId;

    @Column(name = "request_id")
    private String requestId;

    @Enumerated(EnumType.STRING)
    private Source source;

    @Enumerated(EnumType.STRING)
    private LetterType letterType;

    @Enumerated(EnumType.STRING)
    private Language reportLanguage;

    @Enumerated(EnumType.STRING)
    private ResponseType responseType;
}
