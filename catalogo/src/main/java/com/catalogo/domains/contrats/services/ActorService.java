package com.catalogo.domains.contrats.services;

import java.sql.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.entities.Actor;

public interface ActorService extends ProjectionDomainService<Actor, Integer> {
	void repartePremios();
	List<Actor> novedades(Timestamp fecha);
}