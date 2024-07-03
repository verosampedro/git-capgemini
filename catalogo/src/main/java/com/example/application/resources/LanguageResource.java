package com.example.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/idiomas/v1")
public class LanguageResource {
	
	private LanguageService languageService;
	
	public LanguageResource(LanguageService languageService) {
		this.languageService=languageService;
	}
	
	@GetMapping
	public List<Language> getAll() {
		return languageService.getAll();
	}

	@GetMapping(path = "/{id}")
	public Language getOne(@PathVariable int id) throws Exception {
		var language = languageService.getOne(id);
		if(language.isEmpty())
			throw new NotFoundException();
		else
			return language.get();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<Object> add(@Valid @RequestBody Language item) throws Exception {
		if(item == null)
			throw new BadRequestException("Faltan datos");
		var newItem = languageService.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newItem.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public Language modify(@PathVariable int id, @Valid @RequestBody Language item) throws Exception {
		if(item == null)
			throw new BadRequestException("Faltan datos");
		if(id != item.getLanguageId())
			throw new BadRequestException("No coinciden los identificadores");
		return languageService.modify(item);
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws Exception {
		if (languageService.getOne(id).isEmpty())
			throw new BadRequestException("No se ha encontrado idioma con este identificador");
		languageService.deleteById(id);
	}
}