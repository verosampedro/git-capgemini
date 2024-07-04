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

import com.catalogo.domains.contracts.repositories.LanguageRepository;
import com.catalogo.domains.contrats.services.LanguageService;
import com.catalogo.domains.entities.Language;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class LanguageServiceImplTest {
    @MockBean
    LanguageRepository dao;

    @Autowired
    LanguageService srv;

    @Nested
    @DisplayName("Test de la clase LanguageServiceImpl")
    class TestLanguageServiceImpl {
        @Nested
        class TestAdd {
            @Nested
            class KO {
                @Test
                void testAddKO() throws DuplicateKeyException, InvalidDataException {
                    when(dao.save(any(Language.class))).thenReturn(null, null);
                    assertThrows(InvalidDataException.class, () -> srv.add(null));
                    verify(dao, times(0)).save(null);
                }

                @Test
                void testAddDuplicateKeyKO() throws DuplicateKeyException, InvalidDataException {
                    when(dao.findById(1)).thenReturn(Optional.of(new Language(1, "Spanish")));
                    when(dao.existsById(1)).thenReturn(true);
                    assertThrows(DuplicateKeyException.class, () -> srv.add(new Language(1, "English")));
                }
            }
        }

        @Nested
        class TestGetOne {
            @Nested
            class OK {
                @Test
                void testGetOne_valid() {
                    List<Language> lista = new ArrayList<>(Arrays.asList(
                            new Language(1, "Spanish"),
                            new Language(2, "English"),
                            new Language(3, "French")));

                    when(dao.findById(1)).thenReturn(Optional.of(new Language(1, "Spanish")));
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
                // Creación de una lista de lenguajes
                List<Language> lista = new ArrayList<>(Arrays.asList(
                        new Language(1, "Spanish"),
                        new Language(2, "English"),
                        new Language(3, "French")));
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
                    Language language = new Language(1, "German");
                    when(dao.existsById(1)).thenReturn(true);
                    when(dao.save(language)).thenReturn(language);
                    var rslt = srv.modify(language);
                    assertThat(rslt).isEqualTo(language);
                }
            }

            @Nested
            class KO {
                @Test
                void testModify_LanguageNotFound() {
                    Language language = new Language(1, "German");
                    when(dao.existsById(1)).thenReturn(false);
                    assertThrows(NotFoundException.class, () -> srv.modify(language));
                }

                @Test
                void testModify_InvalidLanguage() {
                    Language language = new Language(1, "");
                    when(dao.existsById(1)).thenReturn(true);
                    assertThrows(InvalidDataException.class, () -> srv.modify(language));
                }
            }
        }

        @Nested
        class TestDelete {
            @Nested
            class OK {
                @Test
                void testDelete_Success() throws InvalidDataException {
                    Language language = new Language(1, "German");
                    srv.delete(language);
                    verify(dao, times(1)).delete(language);
                }
            }

            @Nested
            class KO {
                @Test
                void testDelete_NullLanguage() {
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
