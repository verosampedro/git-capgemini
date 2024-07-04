package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contracts.services.ActorService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/actores/v1")
@Tag(name = "actores", description = "Operaciones relacionadas con los actores")
public class ActorResource {
	private ActorService srv;

	public ActorResource(ActorService srv) {
		this.srv = srv;
	}
	
	@GetMapping
	@Operation(summary = "Obtener todos los actores", description = "Devuelve una lista de actores en formato largo o corto")
	public List getAll(@RequestParam(required = false, defaultValue = "largo") @Parameter(description = "Modo de visualización: largo o short") String modo) {
		if("short".equals(modo))
			return srv.getByProjection(ActorShort.class);
		else
			return srv.getByProjection(ActorDTO.class);
	}
	
	
	@GetMapping(params = "page")
	@Operation(summary = "Obtener todos los actores paginados", description = "Devuelve una lista paginada de actores en formato corto")
	public Page<ActorShort> getAll(Pageable page){
		return srv.getByProjection(page, ActorShort.class);
	}
	
	@GetMapping(path = "/{id}")
	@Operation(summary = "Obtener un actor por ID", description = "Devuelve un actor específico por su identificador")
	public ActorDTO getOne(@PathVariable @Parameter(description = "Identificador del actor", required = true) int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}
	
	record Pelicula(int id, String titulo) {}
	
	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	@Operation(summary = "Obtener películas de un actor por identificador", description = "Devuelve una lista de películas asociadas a un actor específico")
	public List<Pelicula> getPelis(@PathVariable @Parameter(description = "Identificador del actor", required = true) int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(o -> new Pelicula(o.getFilm().getFilmId(),o.getFilm().getTitle()))
				.toList();
	}
	
	@PostMapping
	@Operation(summary = "Crear un nuevo actor", description = "Agrega un nuevo actor a la base de datos")
	public ResponseEntity<Object> create(@Valid @RequestBody @Parameter(description = "Datos del nuevo actor", required = true) Actor item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();	
	}
	
	@PostMapping("/corto")
	@Operation(summary = "Crear un nuevo actor en formato corto", description = "Agrega un nuevo actor con datos mínimos")
	public ResponseEntity<Object> createCorto(@Valid @RequestBody @Parameter(description = "Datos del nuevo actor en formato corto", required = true) ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();	
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Actualizar un actor", description = "Actualiza los datos de un actor existente")
	public void update(@PathVariable @Parameter(description = "Identificador del actor", required = true) int id, @Valid @RequestBody @Parameter(description = "Datos del actor actualizados", required = true) ActorDTO item) throws BadRequestException, NotFoundException,  InvalidDataException {
		if(id != item.getActorId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(ActorDTO.from(item));
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Eliminar un actor", description = "Elimina un actor de la base de datos")
	public void delete(@PathVariable @Parameter(description = "Identificador del actor", required = true) int id) throws BadRequestException, NotFoundException,  InvalidDataException {
		srv.deleteById(id);
	}
	
}
