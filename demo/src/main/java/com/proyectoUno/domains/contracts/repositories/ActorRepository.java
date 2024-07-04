package com.proyectoUno.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.proyectoUno.domains.core.contrats.repositories.RepositoryWithProjections;
import com.proyectoUno.domains.entities.Actor;
import com.proyectoUno.domains.entities.models.ActorDTO;
import com.proyectoUno.domains.entities.models.ActorShort;

@RepositoryRestResource(exported = false, path="actores", itemResourceRel="actor", collectionResourceRel="a")
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>,
	RepositoryWithProjections{
	
	//Top 5 ordenados por nombre, orden descendiente
	List<Actor> findTop5ByLastNameStartingWithOrderByFirstNameDesc(String prefijo);
	//Top 5 ordenados por el campo que se indique al llamar al método
	List<Actor> findTop5ByLastNameStartingWith(String prefijo, Sort orderBy);
	
	//Buscar actores con id mayor al indicado
	//Forma 1-Con consulta automática según nombre del método:
	List<Actor> findByActorIdGreaterThanEqual(int actorId);
	//Forma 2-Con JPQL:
	@Query(value = "from Actor a where a.actorId >= ?1") //En JPQL no es necesario poner el SELECT
	List<Actor> findByJPQL(int actorId);
	@Query(value = "SELECT * FROM actor WHERE actor_id >= ?1", nativeQuery = true) //nativeQuery=true para que sepa que la tiene que mandar tal cual a la BBDD
	List<Actor> findBySQL(int id);
	
	//Podemos hacer el método findByActorIdGreaterThanEqual para los objetos ActorDTO y ActorShort
	List<ActorDTO> readByActorIdGreaterThanEqual(int actorId); //"read" hace lo mismo que "find"
	List<ActorShort> queryByActorIdGreaterThanEqual(int actorId); //"query" hace lo mismo que "read" y "find"
	
	/* Con proyecciones */
	//Pero como tenemos que cambiar los sinónimos por cada método para que no se llamen igual, creamos uno de forma genérica --> usando Proyecciones
	<T> List<T> findByActorIdGreaterThanEqual(int actorId, Class<T> proyeccion);
	
	
}
