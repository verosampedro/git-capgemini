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

import com.catalogo.domains.contracts.repositories.CategoryRepository;
import com.catalogo.domains.contrats.services.CategoryService;
import com.catalogo.domains.entities.Category;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class CategoryServiceImplTest {
	@MockBean
	CategoryRepository dao;

	@Autowired
	CategoryService srv;

	@Nested
	@DisplayName("Test de la clase CategoryServiceImpl")
	class testCategoryServiceImpl {
		@Nested
		class testAdd {
			@Nested
			class KO {
				@Test
				void testAddKO() throws DuplicateKeyException, InvalidDataException {
					when(dao.save(any(Category.class))).thenReturn(null, null);
					assertThrows(InvalidDataException.class, () -> srv.add(null));
					verify(dao, times(0)).save(null);
				}

				@Test
				void testAddDuplicateKeyKO() throws DuplicateKeyException, InvalidDataException {
					when(dao.findById(1)).thenReturn(Optional.of(new Category(1, "Comedia")));
					when(dao.existsById(1)).thenReturn(true);
					assertThrows(DuplicateKeyException.class, () -> srv.add(new Category(1, "Drama")));
				}
			}
		}

		@Nested
		class testGetOne {
			@Nested
			class OK {
				@Test
				void testGetOne_valid() {
					List<Category> lista = new ArrayList<>(Arrays.asList(new Category(1, "Comedia"),
							new Category(2, "Acción"), new Category(3, "Drama")));

					when(dao.findById(1)).thenReturn(Optional.of(new Category(1, "Comedia")));
					var rslt = srv.getOne(1);
					assertThat(rslt.isPresent()).isTrue();
				}
			}

			@Nested
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
				// Creación de una lista de categorías
				List<Category> lista = new ArrayList<>(Arrays.asList(new Category(1, "Comedia"),
						new Category(2, "Acción"), new Category(3, "Drama")));
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
					Category category = new Category(1, "Comedia");
					when(dao.existsById(1)).thenReturn(true);
					when(dao.save(category)).thenReturn(category);
					var rslt = srv.modify(category);
					assertThat(rslt).isEqualTo(category);
				}
			}

			@Nested
			class KO {
				@Test
				void testModify_CategoryNotFound() {
					Category category = new Category(1, "Comedia");
					when(dao.existsById(1)).thenReturn(false);
					assertThrows(NotFoundException.class, () -> srv.modify(category));
				}

				@Test
				void testModify_InvalidCategory() {
					Category category = new Category(1, "");
					when(dao.existsById(1)).thenReturn(true);
					assertThrows(InvalidDataException.class, () -> srv.modify(category));
				}
			}
		}

		@Nested
		class testDelete {
			@Nested
			class OK {
				@Test
				void testDelete_Success() throws InvalidDataException {
					Category category = new Category(1, "Comedia");
					srv.delete(category);
					verify(dao, times(1)).delete(category);
				}
			}

			@Nested
			class KO {
				@Test
				void testDelete_NullCategory() {
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
