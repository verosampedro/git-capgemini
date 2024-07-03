package com.example.domains.entities.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class FilmEditDTO {

	private int filmId;
	private String description;
	private Integer length;
	@Pattern(regexp = "^(G|PG|PG-13|R|NC-17)$")
	private String rating;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	@NotNull(message = "Rental duration cannot be null")
    private Byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	@NotBlank
	@Size(min=2, max = 128)public static Film from1(FilmEditDTO dto) {
	    Film film = new Film();
	    film.setFilmId(dto.getFilmId());
	    film.setTitle(dto.getTitle());
	    film.setDescription(dto.getDescription());
	    film.setReleaseYear(dto.getReleaseYear());
	    film.setLanguage(new Language(dto.getLanguageId()));
	    if (dto.getLanguageVOId() != null) {
	        film.setLanguageVO(new Language(dto.getLanguageVOId()));
	    }
	    if (dto.getRentalDuration() != null) {
	        film.setRentalDuration(dto.getRentalDuration().byteValue());
	    } else {
	        film.setRentalDuration((byte) 0); 
	    }
	    if (dto.getRentalRate() != null) {
	        film.setRentalRate(dto.getRentalRate());
	    }
	    if (dto.getLength() != null) {
	        film.setLength(dto.getLength());
	    }
	    if (dto.getReplacementCost() != null) {
	        film.setReplacementCost(dto.getReplacementCost());
	    }
	    if (dto.getRating() != null) {
	        film.setRating(Rating.getEnum(dto.getRating()));
	    }
	    if (dto.getActors() != null) {
	        film.setActors(dto.getActors().stream().map(Actor::new).collect(Collectors.toList()));
	    }
	    if (dto.getCategories() != null) {
	        film.setCategories(dto.getCategories().stream().map(Category::new).collect(Collectors.toList()));
	    }
	    return film;
	}

	private String title;
	@NotNull
	private Integer languageId;
	private Integer languageVOId;
	private List<Integer> actors = new ArrayList<Integer>();
	private List<Integer> categories = new ArrayList<Integer>();

 	public static FilmEditDTO from(Film source) {
		return new FilmEditDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating() == null ? null : source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getLanguageId(),
				source.getActors().stream().map(item -> item.getActorId())
					.collect(Collectors.toList()),
				source.getCategories().stream().map(item -> item.getCategoryId())
					.collect(Collectors.toList())
				);
	}
	public static Film from(FilmEditDTO source) {
		Film result = new Film(
				source.getFilmId(), 
				source.getTitle(),
				source.getDescription(),
				source.getReleaseYear(),
				source.getLanguageId() == null ? null : new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId()),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getLength(),
				source.getReplacementCost(),
				source.getRating() == null ? null : Film.Rating.getEnum(source.getRating())
				);
		source.getActors().stream().forEach(item -> result.addActor(item));
		source.getCategories().stream().forEach(item -> result.addCategory(item));
		return result;
	}

}