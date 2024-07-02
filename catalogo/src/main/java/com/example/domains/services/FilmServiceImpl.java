package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import com.example.domains.contracts.repositories.FilmRepository;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Override
    public Optional<Film> getOne(int id) {
        return filmRepository.findById(id);
    }

    @Override
    public Page<FilmShortDTO> getByProjection(Pageable pageable, Class<FilmShortDTO> type) {
        return filmRepository.findAll(pageable).map(FilmShortDTO::from);
    }

    @Override
    public List<FilmShortDTO> getByProjection(Class<FilmShortDTO> type) {
        return filmRepository.findAll().stream()
                .map(FilmShortDTO::from)
                .toList();
    }

    @Override
    public Film add(FilmShortDTO dto) throws DuplicateKeyException, InvalidDataException {
        Film film = FilmShortDTO.toEntity(dto);
        if (filmRepository.existsById(film.getFilmId())) {
            throw new DuplicateKeyException();
        }
        return filmRepository.save(film);
    }

    @Override
    public void modify(FilmShortDTO dto) throws InvalidDataException {
        Film film = FilmShortDTO.toEntity(dto);
        if (!filmRepository.existsById(film.getFilmId())) {
            throw new InvalidDataException();
        }
        filmRepository.save(film);
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        if (!filmRepository.existsById(id)) {
            throw new NotFoundException();
        }
        filmRepository.deleteById(id);
    }
}