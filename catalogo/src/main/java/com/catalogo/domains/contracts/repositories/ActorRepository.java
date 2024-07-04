package com.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contrats.repositories.ProjectionsAndSpecificationJpaRepository;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.models.ActorDTO;

public interface ActorRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {
	List<Actor> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	List<Actor> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	List<Actor> findByActorIdGreaterThanEqual(int actorId);
	@Query(value = "from Actor a where a.actorId >= ?1")
	List<Actor> findByJPQL(int actorId);

	List<ActorDTO> readByActorIdGreaterThanEqual(int actorId);
	
	<T> List<T> findByActorIdGreaterThanEqual(int actorId, Class<T> proyeccion);
	
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
