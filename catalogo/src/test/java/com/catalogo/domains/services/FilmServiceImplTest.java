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

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class FilmServiceImplTest {
    @MockBean
    FilmRepository dao;

    @Autowired
    FilmService srv;

    @Nested
    @DisplayName("Test de la clase FilmServiceImpl")
    class TestFilmServiceImpl {
        @Nested
        class TestAdd {
            @Nested
            class KO {
                @Test
                void testAddKO() throws DuplicateKeyException, InvalidDataException {
                    when(dao.save(any(Film.class))).thenReturn(null, null);
                    assertThrows(InvalidDataException.class, () -> srv.add(null));
                    verify(dao, times(0)).save(null);
                }

                @Test
                void testAddDuplicateKeyKO() throws DuplicateKeyException, InvalidDataException {
                    when(dao.findById(1)).thenReturn(Optional.of(new Film(1, "Film1", "Description1")));
                    when(dao.existsById(1)).thenReturn(true);
                    assertThrows(DuplicateKeyException.class, () -> srv.add(new Film(1, "Film2", "Description2")));
                }
            }
        }

        
        
        @Nested
        class TestGetOne {
            @Nested
            class OK {
                @Test
                void testGetOne_valid() {
                    List<Film> lista = new ArrayList<>(Arrays.asList(
                            new Film(1, "Film1", "Description1"),
                            new Film(2, "Film2", "Description2"),
                            new Film(3, "Film3", "Description3")));

                    when(dao.findById(1)).thenReturn(Optional.of(new Film(1, "Film1", "Description1")));
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
        class TestGetAll {
            @Test
            void testGetAll_isNotEmpty() {
                // Creación de una lista de películas
                List<Film> lista = new ArrayList<>(Arrays.asList(
                        new Film(1, "Film1", "Description1"),
                        new Film(2, "Film2", "Description2"),
                        new Film(3, "Film3", "Description3")));
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
        class TestModify {
            @Nested
            class OK {
                @Test
                void testModify_Success() throws NotFoundException, InvalidDataException {
                    Film film = new Film(1, "Film1", "Description1");
                    when(dao.existsById(1)).thenReturn(true);
                    when(dao.save(film)).thenReturn(film);
                    var rslt = srv.modify(film);
                    assertThat(rslt).isEqualTo(film);
                }
            }

            @Nested
            class KO {
                @Test
                void testModify_FilmNotFound() {
                    Film film = new Film(1, "Film1", "Description1");
                    when(dao.existsById(1)).thenReturn(false);
                    assertThrows(NotFoundException.class, () -> srv.modify(film));
                }
                @Test
                void testModify_InvalidFilm() {
                    Film film = new Film(1, "", "");
                    when(dao.existsById(1)).thenReturn(true);
                    assertThrows(InvalidDataException.class, () -> srv.modify(film));
                }
            }
        }

        
        
        @Nested
        class TestDelete {
            @Nested
            class OK {
                @Test
                void testDelete_Success() throws InvalidDataException {
                    Film film = new Film(1, "Film1", "Description1");
                    srv.delete(film);
                    verify(dao, times(1)).delete(film);
                }
            }

            @Nested
            class KO {
                @Test
                void testDelete_NullFilm() {
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
