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

import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.FilmEditDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/peliculas/v1")
public class FilmResource {
	private FilmService srv;
	
	public FilmResource(FilmService srv) {
		this.srv = srv;
	}
	
	@GetMapping
	public List getAll(@RequestParam(required = false, defaultValue = "largo") String modo) {
		if("short".equals(modo))
			return srv.getByProjection(FilmShortDTO.class);
		else
			return srv.getByProjection(Film.class);
	}
	
	/* 
	 * Muestra por defecto 20 elementos en cada página
	 */
	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(Pageable page){
		return srv.getByProjection(page, FilmShortDTO.class);
	}
	
	@GetMapping(path = "/{id}")
	public FilmShortDTO getOne(@PathVariable int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmShortDTO.from(item.get());
	}
	
	@GetMapping(path = "/{id}/corto")
	public FilmEditDTO getOneCorto(@PathVariable int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(item.get());
	}
	
	record Act(int id, String firstName, String lastName) {}
	
	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<Act> get(@PathVariable int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(o -> new Act(o.getActor().getActorId(),o.getActor().getFirstName(),o.getActor().getLastName()))
				.toList();
	}
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody Film item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/corto")
	public ResponseEntity<Object> createCorto(@Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getFilmId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if(item == null)
			throw new BadRequestException("Faltan los datos");
		if(id != item.getFilmId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(FilmEditDTO.from(item));	
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws BadRequestException, NotFoundException,  InvalidDataException {
		srv.deleteById(id);
	}
	
	
}
