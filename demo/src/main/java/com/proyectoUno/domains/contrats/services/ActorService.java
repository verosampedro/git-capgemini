package com.proyectoUno.domains.contrats.services;

import com.proyectoUno.domains.core.contracts.services.ProjectionDomainService;
import com.proyectoUno.domains.entities.Actor;

public interface ActorService extends ProjectionDomainService<Actor, Integer> {
	void repartePremios();
}
