package com.example.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.domains.core.contracts.services.DomainService;
import com.example.domains.entities.Language;

public interface LanguageService extends DomainService<Language, Integer> {
	List<Language> novedades(Timestamp fecha);

	Page<Language> getByProjection(Pageable pageable, Class<Language> class1);

	List<Language> getByProjection(Class<Language> class1);
}
