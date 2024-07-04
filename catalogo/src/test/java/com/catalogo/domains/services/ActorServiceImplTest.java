package com.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.catalogo.domains.contracts.repositories.ActorRepository;
import com.catalogo.domains.contrats.services.ActorService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class ActorServiceImplTest {
	@MockBean
	ActorRepository dao;

	@Autowired
	ActorService srv;

	@Nested
	@DisplayName("Test de la clase ActorServiceImpl")
	class testActorServiceImpl {
		@Nested
		class testAdd {
			@Nested
			class KO {
				@Test
				void testAddKO() throws DuplicateKeyException, InvalidDataException {
					when(dao.save(any(Actor.class))).thenReturn(null, null);
					assertThrows(InvalidDataException.class, () -> srv.add(null));
					verify(dao, times(0)).save(null);
				}

				@Test
				void testAddDuplicateKeyKO() throws DuplicateKeyException, InvalidDataException {
					when(dao.findById(1)).thenReturn(Optional.of(new Actor(1, "Pepito", "GRILLO")));
					when(dao.existsById(1)).thenReturn(true);
					assertThrows(DuplicateKeyException.class, () -> srv.add(new Actor(1, "PP", "ILLO")));
				}
			}
		}

		
		
		@Nested
		class testGetOne {
			@Nested
			class OK {
				@Test
				void testGetOne_valid() {
					List<Actor> lista = new ArrayList<>(Arrays.asList(new Actor(1, "Pepito", "GRILLO"),
							new Actor(2, "Carmelo", "COTON"), new Actor(3, "Capitan", "TAN")));

					when(dao.findById(1)).thenReturn(Optional.of(new Actor(1, "Pepito", "GRILLO")));
					var rslt = srv.getOne(1);
					assertThat(rslt.isPresent()).isTrue();
				}
			}

			class KO {
				@Test
				void testGetOne_notfound() {
					when(dao.findById(1)).thenReturn(Optional.empty());
					var rslt = srv.getOne(1);
					assertThat(rslt.isEmpty()).isTrue();
				}
			}
		}
		
		
		
		@Nested
		class testGetAll {
			@Test
			void testGetAll_isNotEmpty() {
				// Creación de una lista de actores
				List<Actor> lista = new ArrayList<>(Arrays.asList(new Actor(1, "Pepito", "GRILLO"),
						new Actor(2, "Carmelo", "COTON"), new Actor(3, "Capitan", "TAN")));
				// Mockeo del método findAll
				when(dao.findAll()).thenReturn(lista);
				// Llamada al método getAll del servicio
				var rslt = srv.getAll();
				// Verificación del resultado
				assertThat(rslt.size()).isEqualTo(3);
				// Verificación de la interacción con el Mock
				verify(dao, times(1)).findAll();
			}
		}

		
		@Nested
		class testModify {
			@Nested
			class OK {
				@Test
				void testModify_Success() throws NotFoundException, InvalidDataException {
					Actor actor = new Actor(1, "John", "Doe");
					when(dao.existsById(1)).thenReturn(true);
					when(dao.save(actor)).thenReturn(actor);
					var rslt = srv.modify(actor);
					assertThat(rslt).isEqualTo(actor);
				}
			}

			@Nested
			class KO {
				@Test
				void testModify_ActorNotFound() {
					Actor actor = new Actor(1, "John", "Doe");
					when(dao.existsById(1)).thenReturn(false);
					assertThrows(NotFoundException.class, () -> srv.modify(actor));
				}

				@Test
				void testModify_InvalidActor() {
					Actor actor = new Actor(1, "", "");
					when(dao.existsById(1)).thenReturn(true);
					assertThrows(InvalidDataException.class, () -> srv.modify(actor));
				}
			}
		}

		
		
		@Nested
		class testDelete {
			@Nested
			class OK {
				@Test
				void testDelete_Success() throws InvalidDataException {
					Actor actor = new Actor(1, "John", "Doe");
					srv.delete(actor);
					verify(dao, times(1)).delete(actor);
				}
			}

			@Nested
			class KO {
				@Test
				void testDelete_NullActor() {
					assertThrows(InvalidDataException.class, () -> srv.delete(null));
				}
			}
		}



		@Nested
		class TestDeleteById {
			@Test
			void testDeleteById_Success() {
				srv.deleteById(1);
				verify(dao, times(1)).deleteById(1);
			}
		}
	}
}
