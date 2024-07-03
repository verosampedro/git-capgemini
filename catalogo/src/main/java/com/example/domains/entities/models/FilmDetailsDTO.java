package com.example.domains.entities.models;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Film;

import lombok.Value;

@Value
public class FilmDetailsDTO {

	private int filmId;
	private String description;
	private Integer length;
	private String rating;
	private Short releaseYear;
	private Byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	private String title;
	private String language;
	private String languageVO;
	private List<String> actors;
	private List<String> categories;
	
	public static FilmDetailsDTO from(Film source) {
		return new FilmDetailsDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating().getValue(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getName(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
				source.getActors().stream().map(item -> item.getFirstName() + " " + item.getLastName())
					.sorted().toList(),
				source.getCategories().stream().map(item -> item.getName()).sorted().toList()
				);
	}
}