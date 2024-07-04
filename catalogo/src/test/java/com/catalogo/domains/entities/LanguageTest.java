package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LanguageTest {

	Language lenguaje;
	List<Actor> lenguajes = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		lenguaje = new Language();
		lenguajes = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Es un lenguaje valido")
			void testIsValid() {
				var fixture = new Language();
				fixture.setLanguageId(100);
				fixture.setName("Asturiano");
				assertTrue(fixture.isValid());
			}
			@Test
			@DisplayName("Solo compara la PK")
			void testPrimaryKeyOK() {
				var fixture1 = new Language();
				fixture1.setLanguageId(666);
				fixture1.setName("Asturiano");
				var fixture2 = new Language();
				fixture2.setLanguageId(666);
				fixture2.setName("Asturiano");
				assertAll("PK", 
						() -> assertTrue(fixture1.equals(fixture2)),
						() -> assertTrue(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() == fixture2.hashCode())
						);
			}
			@Test
			@DisplayName("Solo la PK diferente")
			void testPrimaryKeyKO() {
				var fixture1 = new Language();
				fixture1.setLanguageId(666);
				fixture1.setName("Asturiano");
				var fixture2 = new Language();
				fixture2.setLanguageId(665);
				fixture2.setName("Asturiano");
				assertAll("PK", 
						() -> assertFalse(fixture1.equals(fixture2)),
						() -> assertFalse(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() != fixture2.hashCode())
						);
			}
		}
		@Nested
		class KO {
			@DisplayName("El name debe ser una cadena de máximo 20 caracteres")
			@ParameterizedTest(name = "name: -{0}- -> {1}")
			@CsvSource(value = {"'Asturiano de nueva creacion en Gijon','ERRORES: name: debe ser una cadena de máximo 20 caracteres'"})
			public void testNameNoValido(String valor, String error) {
				lenguaje.setName(valor);
				assertTrue(lenguaje.isInvalid());
			}
		}
	}
	
}
