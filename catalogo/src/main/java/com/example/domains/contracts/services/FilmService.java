package com.example.domains.contracts.services;

import java.util.List;
import java.util.Optional;

import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {
    Optional<Film> getOne(int id);

    Page<FilmShortDTO> getByProjection(Pageable pageable, Class<FilmShortDTO> type);

    List<FilmShortDTO> getByProjection(Class<FilmShortDTO> type);

    Film add(Film film) throws DuplicateKeyException, InvalidDataException;

    Film modify(Film film) throws InvalidDataException;

    void deleteById(int id) throws NotFoundException;
}
