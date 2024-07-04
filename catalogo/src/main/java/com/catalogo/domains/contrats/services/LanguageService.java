package com.catalogo.domains.contrats.services;

import java.sql.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contracts.services.DomainService;
import com.catalogo.domains.entities.Language;

public interface LanguageService extends DomainService<Language, Integer> {
	List<Language> novedades(Timestamp fecha);
}
