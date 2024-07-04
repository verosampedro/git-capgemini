package com.catalogo.domains.entities.models;

import java.util.List;

import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class NovedadesDTO {
	private List<FilmShortDTO> films;
	private List<ActorDTO> actors;
	private List<Category> categories;
	private List<Language> languages;
	
}