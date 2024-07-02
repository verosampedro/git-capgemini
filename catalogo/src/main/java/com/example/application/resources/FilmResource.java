package com.example.application.resources;

import java.net.URI;
import java.util.List;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.domains.entities.Film;
import com.example.domains.entities.models.FilmShortDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@RestController
@RequestMapping(path = "api/peliculas/v1")
public class FilmResource {
    @Autowired
    private FilmService srv;

    @GetMapping(params = "page")
    public Page<FilmShortDTO> getAll(Pageable pageable,
                                     @RequestParam(defaultValue = "short") String modo) {
        return srv.getByProjection(pageable, FilmShortDTO.class);
    }

    @GetMapping(params = "modo")
    public List<?> getAllFilms(@RequestParam(defaultValue = "short") String modo) {
        if ("short".equals(modo)) {
            return srv.getByProjection(FilmShortDTO.class);
        } else {
            return srv.getByProjection(Film.class);
        }
    }

    @GetMapping
    public List<FilmShortDTO> getAll() {
        return srv.getByProjection(FilmShortDTO.class);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FilmShortDTO> getOne(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return ResponseEntity.ok(FilmShortDTO.from(item.get()));
    }

    record Reparto(int id, String nombre) {}

    @GetMapping(path = "/{id}/reparto")
    @Transactional
    public List<Reparto> getReparto(@PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmActors().stream()
                   .map(o -> new Reparto(o.getActor().getActorId(), o.getActor().getFirstName()))
                   .toList();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Film item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(FilmShortDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(((Film) newItem).getFilmId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody Film item) throws BadRequestException, NotFoundException, InvalidDataException {
        if (id != item.getFilmId())
            throw new BadRequestException("No coinciden los id");
        srv.modify(FilmShortDTO.from(item));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws NotFoundException, InvalidDataException, BadRequestException {
        srv.deleteById(id);
    }
}