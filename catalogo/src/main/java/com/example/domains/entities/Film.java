package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.example.domains.core.entities.EntityBase;
import com.example.domains.entities.models.FilmShortDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "film")
@NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f")
public class Film extends EntityBase<Film> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static enum Rating {
        GENERAL_AUDIENCES("G"), PARENTAL_GUIDANCE_SUGGESTED("PG"), PARENTS_STRONGLY_CAUTIONED("PG-13"), RESTRICTED("R"), ADULTS_ONLY("NC-17");

        String value;

        Rating(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Rating getEnum(String value) {
            switch (value) {
                case "G":
                    return Rating.GENERAL_AUDIENCES;
                case "PG":
                    return Rating.PARENTAL_GUIDANCE_SUGGESTED;
                case "PG-13":
                    return Rating.PARENTS_STRONGLY_CAUTIONED;
                case "R":
                    return Rating.RESTRICTED;
                case "NC-17":
                    return Rating.ADULTS_ONLY;
                case "":
                    return null;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + value);
            }
        }

        public static final String[] VALUES = { "G", "PG", "PG-13", "R", "NC-17" };
    }

    @Converter
    private static class RatingConverter implements AttributeConverter<Rating, String> {
        @Override
        public String convertToDatabaseColumn(Rating rating) {
            return rating == null ? null : rating.getValue();
        }

        @Override
        public Rating convertToEntityAttribute(String value) {
            return value == null ? null : Rating.getEnum(value);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", unique = true, nullable = false)
    private int filmId;

    @Lob
    private String description;

    @Column(name = "last_update", insertable = false, updatable = false, nullable = false)
    private Timestamp lastUpdate;

    @Positive
    private Integer length;

    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Min(1901)
    @Max(2155)
    @Column(name = "release_year")
    private Short releaseYear;

    @NotNull
    @Positive
    @Column(name = "rental_duration", nullable = false)
    private byte rentalDuration;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "rental_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentalRate;

    @NotNull
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "replacement_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal replacementCost;

    @NotBlank
    @Size(max = 128)
    @Column(nullable = false, length = 128)
    private String title;

    @ManyToOne
    @JoinColumn(name = "language_id")
    @NotNull
    @JsonManagedReference
    private Language language;

    @ManyToOne
    @JoinColumn(name = "original_language_id")
    @JsonManagedReference
    private Language languageVO;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<FilmActor> filmActors = new ArrayList<FilmActor>();

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();

    public Film() {
    }

    public Film(int filmId) {
        this.filmId = filmId;
    }

    public Film(@NotBlank @Size(max = 128) String title, @NotNull Language language, @Positive byte rentalDuration,
            @Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
            @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost) {
        super();
        this.title = title;
        this.language = language;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public Film(int filmId, @NotBlank @Size(max = 128) String title, @NotNull Language language,
            @NotNull @Positive byte rentalDuration,
            @NotNull @Digits(integer = 2, fraction = 2) @DecimalMin(value = "0.0", inclusive = false) BigDecimal rentalRate,
            @NotNull @Digits(integer = 3, fraction = 2) @DecimalMin(value = "0.0", inclusive = false) BigDecimal replacementCost) {
        super();
        this.filmId = filmId;
        this.title = title;
        this.language = language;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public Film(int filmId, @NotBlank @Size(max = 128) String title, String description, @Min(1901) Short releaseYear,
            @NotNull Language language, Language languageVO, @Positive byte rentalDuration,
            @Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
            @Positive Integer length,
            @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost,
            Rating rating) {
        super();
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.languageVO = languageVO;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
    }

    public int getFilmId() {
        return this.filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
        if (filmActors != null && !filmActors.isEmpty())
            filmActors.forEach(item -> {
                if (item.getId().getFilmId() != filmId)
                    item.getId().setFilmId(filmId);
            });
        if (filmCategories != null && !filmCategories.isEmpty())
            filmCategories.forEach(item -> {
                if (item.getId().getFilmId() != filmId)
                    item.getId().setFilmId(filmId);
            });
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Short getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public byte getRentalDuration() {
        return this.rentalDuration;
    }

    public void setRentalDuration(byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return this.rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public BigDecimal getReplacementCost() {
        return this.replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLanguageVO() {
        return this.languageVO;
    }

    public void setLanguageVO(Language languageVO) {
        this.languageVO = languageVO;
    }

    public List<Actor> getActors() {
        return this.filmActors.stream().map(FilmActor::getActor).toList();
    }

    public void setActors(List<Actor> source) {
        if (filmActors == null || !filmActors.isEmpty())
            clearActors();
        source.forEach(this::addActor);
    }

    public void clearActors() {
        filmActors.clear();
    }

    public void addActor(Actor actor) {
        if (filmActors.stream().anyMatch(item -> item.getActor().equals(actor))) return;
        FilmActor filmActor = new FilmActor(this, actor);
        filmActors.add(filmActor);
    }

    public void addActor(int actorId) {
        addActor(new Actor(actorId));
    }

    public void removeActor(Actor actor) {
        filmActors.removeIf(item -> item.getActor().equals(actor));
    }

    public void removeActor(int actorId) {
        removeActor(new Actor(actorId));
    }

    public List<Category> getCategories() {
        return this.filmCategories.stream().map(FilmCategory::getCategory).toList();
    }

    public void setCategories(List<Category> source) {
        if (filmCategories == null || !filmCategories.isEmpty())
            clearCategories();
        source.forEach(this::addCategory);
    }

    public void clearCategories() {
        filmCategories.clear();
    }

    public void addCategory(Category item) {
        if (filmCategories.stream().anyMatch(category -> category.getCategory().equals(item))) return;
        FilmCategory filmCategory = new FilmCategory(this, item);
        filmCategories.add(filmCategory);
    }

    public void addCategory(int id) {
        addCategory(new Category(id));
    }

    public void removeCategory(Category ele) {
        filmCategories.removeIf(item -> item.getCategory().equals(ele));
    }

    public void removeCategory(int id) {
        removeCategory(new Category(id));
    }

    public List<FilmActor> getFilmActors() {
        return filmActors;
    }

    public void setFilmActors(List<FilmActor> filmActors) {
        this.filmActors = filmActors;
    }

    public List<FilmCategory> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(List<FilmCategory> filmCategories) {
        this.filmCategories = filmCategories;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Film o)
            return filmId == o.filmId;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Film [filmId=" + filmId + ", title=" + title + ", rentalDuration=" + rentalDuration + ", rentalRate="
                + rentalRate + ", replacementCost=" + replacementCost + ", lastUpdate=" + lastUpdate + ", description="
                + description + ", length=" + length + ", rating=" + rating + ", releaseYear=" + releaseYear
                + ", language=" + language + ", languageVO=" + languageVO + "]";
    }

    public Film merge(Film target) {
        target.title = title;
        target.description = description;
        target.releaseYear = releaseYear;
        target.language = language;
        target.languageVO = languageVO;
        target.rentalDuration = rentalDuration;
        target.rentalRate = rentalRate;
        target.length = length;
        target.replacementCost = replacementCost;
        target.rating = rating;
        target.getActors().stream().filter(item -> !getActors().contains(item))
                .forEach(target::removeActor);
        getActors().stream().filter(item -> !target.getActors().contains(item)).forEach(target::addActor);
        target.getCategories().stream().filter(item -> !getCategories().contains(item))
                .forEach(target::removeCategory);
        getCategories().stream().filter(item -> !target.getCategories().contains(item))
                .forEach(target::addCategory);
        return target;
    }

    public static FilmShortDTO from(Film item) {
        // TODO Auto-generated method stub
        return null;
    }

}
