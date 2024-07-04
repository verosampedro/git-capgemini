package com.proyectoUno.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.proyectoUno.domains.core.contrats.repositories.ProjectionsAndSpecificationJpaRepository;
import com.proyectoUno.domains.entities.Film;


@RepositoryRestResource(path="peliculas", itemResourceRel="pelicula", collectionResourceRel="peliculas")
public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer>{
	@RestResource(path = "por-titulo")
	List<Film> findByTitleStartingWith(String nombre);
}
