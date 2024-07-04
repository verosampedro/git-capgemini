package com.catalogo.domains.contrats.services;

import java.sql.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.core.contracts.services.SpecificationDomainService;
import com.catalogo.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>, SpecificationDomainService<Film, Integer> {
	List<Film> novedades(Timestamp fecha);
}
