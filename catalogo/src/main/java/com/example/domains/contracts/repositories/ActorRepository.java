package com.example.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;
import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Actor;


public interface ActorRepository extends ProjectionsAndSpecificationJpaRepository<Actor, Integer> {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);

	List<Actor> findBySQL(int i);

	List<Actor> findByJPQL(int i);

	List<Actor> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String string);
}