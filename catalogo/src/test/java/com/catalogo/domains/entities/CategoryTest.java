package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

	Category categoria;
	List<Category> categorias = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		categoria = new Category();
		categorias = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK{
			@Test
			@DisplayName("Es una categoria valida")
			void testIsValid() {
				var fixture = new Category();
				fixture.setCategoryId(600);
				fixture.setName("Nueva categoria");
				fixture.setLastUpdate(new Timestamp(System.currentTimeMillis()));
				assertTrue(fixture.isValid());
			}
			@Test
			@DisplayName("Solo compara la PK")
			void testPrimaryKeyOK() {
				var fixture1 = new Category();
				fixture1.setCategoryId(666);
				fixture1.setName("Nueva");
				var fixture2 = new Category();
				fixture2.setCategoryId(666);
				fixture2.setName("Nueva2");
				assertAll("PK", 
						() -> assertTrue(fixture1.equals(fixture2)),
						() -> assertTrue(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() == fixture2.hashCode())
						);
			}
			@Test
			@DisplayName("Solo la PK diferente")
			void testPrimaryKeyKO() {
				var fixture1 = new Category();
				fixture1.setCategoryId(666);
				fixture1.setName("Nueva");
				var fixture2 = new Category();
				fixture2.setCategoryId(665);
				fixture2.setName("Nueva");
				assertAll("PK", 
						() -> assertFalse(fixture1.equals(fixture2)),
						() -> assertFalse(fixture2.equals(fixture1)),
						() -> assertTrue(fixture1.hashCode() != fixture2.hashCode())
						);
			}
		}
		@Nested
		class KO {
			@DisplayName("El name debe ser una cadena de máximo 25 caracteres")
			@ParameterizedTest(name = "name: -{0}- -> {1}")
			@CsvSource(value = {"'Documentaries on Climate Change','ERRORES: name: debe ser una cadena de máximo 25 caracteres'"})
			public void testNameNoValido(String valor, String error) {
				categoria.setName(valor);
				assertTrue(categoria.isInvalid());
			}
		}
	}

}
