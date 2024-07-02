package com.example.domains.entities.models;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmShortDTO(@JsonProperty("id") int filmId, @JsonProperty("titulo") String title) {

    public static Film from(FilmShortDTO dto) {
        Film film = new Film();
        film.setFilmId(dto.filmId());
        film.setTitle(dto.title());
        return film;
    }

    public static FilmShortDTO from(Film film) {
        return new FilmShortDTO(film.getFilmId(), film.getTitle());
    }

    public static Film toEntity(FilmShortDTO dto) {
        Film film = new Film();
        film.setFilmId(dto.filmId());
        film.setTitle(dto.title());
        return film;
    }
}