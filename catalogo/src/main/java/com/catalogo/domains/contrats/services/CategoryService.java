package com.catalogo.domains.contrats.services;

import java.sql.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contracts.services.DomainService;
import com.catalogo.domains.entities.Category;

public interface CategoryService extends DomainService<Category, Integer> {
	List<Category> novedades(Timestamp fecha);
}