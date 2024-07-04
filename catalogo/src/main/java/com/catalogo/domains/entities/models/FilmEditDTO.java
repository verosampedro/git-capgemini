package com.catalogo.domains.entities.models;

import java.math.BigDecimal;

import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.Language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class FilmEditDTO {
	private int filmId;
	private String description;
	private Integer length;
	private String rating;
	private Short releaseYear;
	@NotNull
	private Byte rentalDuration;
	@NotNull
	private BigDecimal rentalRate;
	@NotNull
	private BigDecimal replacementCost;
	@NotBlank
	@Size(min=2, max = 128)
	private String title;
	@NotNull
	private int languageId;
	private Integer languageVOId;
	
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
				source.getLanguage().getLanguageId(),
				source.getLanguageVO() == null ? null : (Integer)source.getLanguageVO().getLanguageId());
	}
 	
 	public static Film from(FilmEditDTO source) {
 		return new Film(
				source.getFilmId(), 
				source.getTitle(),
				source.getDescription(),
				source.getReleaseYear(),
				new Language(source.getLanguageId()),
				source.getLanguageVOId() == null ? null : new Language(source.getLanguageVOId()),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getLength(),
				source.getReplacementCost());
 	}

	public FilmEditDTO(int filmId, String description, Integer length, String rating, Short releaseYear,
			@NotNull Byte rentalDuration, @NotNull BigDecimal rentalRate, @NotNull BigDecimal replacementCost,
			@NotBlank @Size(min = 2, max = 128) String title, @NotNull int languageId, Integer languageVOId) {
		super();
		this.filmId = filmId;
		this.description = description;
		this.length = length;
		this.rating = rating;
		this.releaseYear = releaseYear;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
		this.title = title;
		this.languageId = languageId;
		this.languageVOId = languageVOId;
	}


}