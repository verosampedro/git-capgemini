package com.example.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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

import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.FilmDetailsDTO;
import com.example.domains.entities.models.FilmEditDTO;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.NotFoundException;


@RestController
@RequestMapping(path = "/api/peliculas/v1")
public class FilmResource {
	
	private FilmService filmService;
	
	public FilmResource(FilmService filmService) {
		this.filmService=filmService;
	}
	
	@GetMapping
	public List<FilmShortDTO> getAll(@RequestParam(defaultValue = "short") String mode) {
		return filmService.getByProjection(FilmShortDTO.class);
	}
	
	@GetMapping(params = "mode=detail")
	public List<FilmDetailsDTO> getAllDetail(@RequestParam(defaultValue = "short") String mode) {
		return filmService.getAll().stream().map(item -> FilmDetailsDTO.from(item)).toList();
	}
	
	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAllShortPage(Pageable page) {
		return filmService.getByProjection(page, FilmShortDTO.class);
	}

	@GetMapping(path = "/{id}")
	public FilmShortDTO getOneShort(@PathVariable int id, @RequestParam(defaultValue = "short") String mode)
			throws Exception {
		Optional<Film> result = filmService.getOne(id);
		if (result.isEmpty())
			throw new NotFoundException();
		return FilmShortDTO.from(result.get());
	}

	@GetMapping(path = "/{id}", params = "mode=detail")
	public FilmDetailsDTO getOneDetail( @PathVariable int id, @RequestParam String mode)
			throws Exception {
		Optional<Film> result = filmService.getOne(id);
		if (result.isEmpty())
			throw new NotFoundException();
		return FilmDetailsDTO.from(result.get());
	}

	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getCast( @PathVariable int id)
			throws Exception {
		Optional<Film> result = filmService.getOne(id);
		if (result.isEmpty())
			throw new NotFoundException();
		return result.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
	}


	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(@PathVariable int id)
			throws Exception {
		Optional<Film> result = filmService.getOne(id);
		if (result.isEmpty())
			throw new NotFoundException();
		return result.get().getCategories();
	}

	@GetMapping(path = "/calificaciones")
	public List<Map<String, String>> getRatings() {
		return List.of(Map.of("key", "G", "value", "Todos los públicos"),
				Map.of("key", "PG", "value", "Guía paternal sugerida"),
				Map.of("key", "PG-13", "value", "Guía paternal estricta"), 
				Map.of("key", "R", "value", "Restringido"),
				Map.of("key", "NC-17", "value", "Prohibido para audiencia de 17 años y menos"));
	}


	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(@RequestBody FilmEditDTO item) throws Exception {
	    Film newItem = filmService.add(FilmEditDTO.from(item));
	    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newItem.getFilmId())
	            .toUri();
	    return ResponseEntity.created(location).build();
	}


	@Transactional
	@PutMapping(path = "/{id}")
	public FilmEditDTO modify( @PathVariable int id, @Valid @RequestBody FilmEditDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		return FilmEditDTO.from(filmService.modify(FilmEditDTO.from(item)));
	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable int id) throws Exception {
		if (filmService.getOne(id).isEmpty())
			throw new BadRequestException("No encontrada pelicula con este identificador");
		filmService.deleteById(id);
	}
}