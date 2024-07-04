package com.proyectoUno.domains.entities.models;

import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyectoUno.domains.entities.Film;

@Projection(name = "peli-corta", types= {Film.class})
public interface FilmShortDTO{
	@JsonProperty("id")
	int getFilmId();
	@JsonProperty("titulo")
	String getTitle();
}