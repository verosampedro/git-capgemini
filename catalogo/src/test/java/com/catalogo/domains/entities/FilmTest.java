package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Pruebas de la clase Film")
@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest
public class FilmTest {
	Film film;
	List<Film> films = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		film = new Film();
		films = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Es una pelicula valida")
			void testIsValid() {
				// Crear una instancia de Language para usar en la película
		        Language language = new Language();
		        language.setLanguageId(1);
		        language.setName("English");

		        // Crear una instancia válida de Film
		        Film peliculaValida = new Film();
		        peliculaValida.setFilmId(1);
		        peliculaValida.setTitle("Inception");
		        peliculaValida.setDescription("A mind-bending thriller by Christopher Nolan.");
		        peliculaValida.setReleaseYear((short) 2010);
		        peliculaValida.setRentalDuration((byte) 7);
		        peliculaValida.setRentalRate(new BigDecimal("4.99"));
		        peliculaValida.setReplacementCost(new BigDecimal("19.99"));
		        peliculaValida.setLength(148); // Duración en minutos
		        peliculaValida.setRating(Film.Rating.getEnum("PG-13"));
		        peliculaValida.setLanguage(language);
		        peliculaValida.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		        assertTrue(peliculaValida.isValid());
			}
			@Test
			@DisplayName("Solo compara la PK")
			void testPrimaryKeyOK() {
				var fixture1 = new Film(666, "Pelicula nueva de Inside Out",(short)2024);
				var fixture2 = new Film(666, "Pelicula nueva de Inside Out",(short)2024);
				assertAll("PK", 
						() -> assertTrue(fixture1.equals(fixture2)),
						() -> assertTrue(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() == fixture2.hashCode())
						);
			}

			@Test
			@DisplayName("Solo la PK diferente")
			void testPrimaryKeyKO() {
				var fixture1 = new Film(666, "Pelicula nueva de Inside Out",(short)2024);
				var fixture2 = new Film(665, "Pelicula nueva de Inside Out",(short)2024);
				assertAll("PK", 
						() -> assertFalse(fixture1.equals(fixture2)),
						() -> assertFalse(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() != fixture2.hashCode())
						);
			}
		}
		@Nested
		class KO{
			@DisplayName("La length debe ser un numero entre 1 y 1000")
			@ParameterizedTest(name = "length: -{0}- -> {1}")
			@CsvSource(value = {"-1,'ERRORES: length: debe ser un numero entre 1 y 1000'",
								"0,'ERRORES: length: debe ser un numero entre 1 y 1000'",
								"10001,'ERRORES: length: debe ser un numero entre 1 y 1000'"})
			public void testLengthNoValida(Integer valor, String error) {
				film.setLength(valor);
				assertTrue(film.isInvalid());
			}
			@Test
			@DisplayName("Rating no válido")
			public void testRatingNoValido() {
			    film.setRating(null);
			        
				assertTrue(film.isInvalid());
			}
			@DisplayName("El rental rate debe tener máximo 4 enteros y 2 decimales")
			@ParameterizedTest(name = "rental rate: -{0}- -> {1}")
			@CsvSource(value = {"12345.67,'ERRORES: rental rate: debe tener máximo 4 enteros y 2 decimales'"})
			public void testRentalRateNoValido(double valor, String error) {
				film.setRentalRate(new BigDecimal(valor));
		        assertTrue(film.isInvalid());
			}
			@DisplayName("El replacement cost debe tener máximo 5 enteros y 2 decimales")
			@ParameterizedTest(name = "replacement cost: -{0}- -> {1}")
			@CsvSource(value = {"123456.78,'ERRORES: replacement cost: debe tener máximo 5 enteros y 2 decimales'"})
			public void testReplacementCostNoValido(double valor, String error) {
				film.setReplacementCost(new BigDecimal(valor));
		        assertTrue(film.isInvalid());
			}
			@DisplayName("El titulo debe ser una cadena y no ser nula")
			@ParameterizedTest(name = "title: -{0}- -> {1}")
			@CsvSource(value = {"'','ERRORES: title: debe ser una cadena y no ser nula'"})
			public void testTitleNoValido(String valor, String error) {
				film.setTitle(valor);
				assertTrue(film.isInvalid());
			}
		}
	}
    
}