package com.catalogo.domains.contracts.repositories;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.catalogo.domains.core.contrats.repositories.ProjectionsAndSpecificationJpaRepository;
import com.catalogo.domains.entities.Film;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer>{
	// Encuentra películas por título (case insensitive)
    List<Film> findByTitleIgnoreCase(String title);

    // Encuentra películas por año de lanzamiento
    List<Film> findByReleaseYear(Short releaseYear);

    // Encuentra películas por calificación (rating)
    List<Film> findByRating(String rating);

    // Encuentra películas con una duración de alquiler específica
    List<Film> findByRentalDuration(byte rentalDuration);

    // Encuentra películas por una tasa de alquiler mayor o igual a un valor específico
    List<Film> findByRentalRateGreaterThanEqual(BigDecimal rentalRate);
    
    List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}
