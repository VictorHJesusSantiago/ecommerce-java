package com.ecommerce.repository;

import com.ecommerce.model.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    Optional<EmailTemplate> findByCode(String code);

    Optional<EmailTemplate> findByCodeAndLocale(String code, String locale);

    boolean existsByCode(String code);

    List<EmailTemplate> findByIsActiveTrue();

    List<EmailTemplate> findByLocale(String locale);
}
