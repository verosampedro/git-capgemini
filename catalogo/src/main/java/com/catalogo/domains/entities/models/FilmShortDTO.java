package com.catalogo.domains.entities.models;

import com.catalogo.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmShortDTO(@JsonProperty("id") int filmId, @JsonProperty("titulo") String title) {
	public static FilmShortDTO from(Film source) {
		return new FilmShortDTO(source.getFilmId(), source.getTitle());
	}
}