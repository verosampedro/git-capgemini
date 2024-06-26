package com.example.domains.entities.models;

import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class FilmDTO {
	private List<Film> films;
	private List<Actor> actors;
	private List<Category> categories;
	private List<Language> languages;
	
}