package dev.verosampedro.demo_maven;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

class PersonaTest {

	@Nested
	@DisplayName("Proceso de instanciacion")
	class Create {
		@Nested
		class OK {
			@Test
			void soloNombre() {
				var persona = new Persona(1, "Verónica");
				assertNotNull(persona);
				assertAll("Persona", () -> assertEquals(1, persona.getId(), "id"),
						() -> assertEquals("Verónica", persona.getNombre(), "nombre"),
						() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}

			@ParameterizedTest(name =  "{0} {1}")
			@CsvSource(value = { "1, Verónica", "2, Verónica Sampedro", "3,'Sampedro, Verónica'" })
			void soloNombre(int id, String nombre) {
				var persona = new Persona(id, nombre);
				assertNotNull(persona);
				assertAll("Persona", () -> assertEquals(id, persona.getId(), "id"),
						() -> assertEquals(nombre, persona.getNombre(), "nombre"),
						() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
			@ParameterizedTest(name =  "{0} {1}")
			@CsvSource(value = { "1, Verónica", "2, Verónica, Sampedro", "3,'Sampedro, Verónica'" })
			@Disabled
			void soloNombre(ArgumentsAccessor args) {
				var persona = args.size() == 3 ? 
						new Persona(args.getInteger(0), args.getString(1), args.getString(2)) :
						new Persona(args.getInteger(0), args.getString(1));
				assertNotNull(persona);
				assertAll("Persona", () -> assertEquals(args.getInteger(0), persona.getId(), "id"),
						() -> assertEquals(args.getString(1), persona.getNombre(), "nombre"),
						() -> assertTrue(args.size() == 3 ? persona.getApellidos().isPresent():persona.getApellidos().isEmpty(), "apellidos"));
			}
		}

		@Nested
		class KO {
			@ParameterizedTest(name =  "{0} {1}")
			@CsvSource(value = { "3,","4,''","5,'    '" })
			void soloNombre(int id, String nombre) {
				assertThrows(Exception.class, () -> new Persona(id, nombre));
			}
		}
	}

}