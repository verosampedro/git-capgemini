package com.catalogo.application.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.FilmEditDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService srv;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllString() throws Exception {
        List<FilmShortDTO> lista = new ArrayList<>(
                Arrays.asList(new FilmShortDTO(1, "Film1"),
                        new FilmShortDTO(2, "Film2"),
                        new FilmShortDTO(3, "Film3")));
        when(srv.getByProjection(FilmShortDTO.class)).thenReturn(lista);
        mockMvc.perform(get("/api/peliculas/v1?modo=short").accept(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.size()").value(3));
    }

    @Test
    void testGetAllPageable() throws Exception {
        List<FilmShortDTO> lista = new ArrayList<>(
                Arrays.asList(new FilmShortDTO(1, "Film1"),
                        new FilmShortDTO(2, "Film2"),
                        new FilmShortDTO(3, "Film3")));

        when(srv.getByProjection(PageRequest.of(0, 20), FilmShortDTO.class))
                .thenReturn(new PageImpl<>(lista));
        mockMvc.perform(get("/api/peliculas/v1").queryParam("page", "0"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.content.size()").value(3),
                        jsonPath("$.size").value(3));
    }
    
    @Test
    void testGetOneCorto() throws Exception {
        int id = 1;
        var filmEditDTO = new FilmEditDTO(1, "Description", 120, "PG-13", (short) 2022, (byte) 5, BigDecimal.valueOf(4.99), BigDecimal.valueOf(19.99), "Film1", 1, null);
        var film = FilmEditDTO.from(filmEditDTO);
        when(srv.getOne(id)).thenReturn(Optional.of(film));

        mockMvc.perform(get("/api/peliculas/v1/{id}/corto", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(1))
                .andExpect(jsonPath("$.title").value("Film1"))
                .andDo(print());
    }

    @Test
    void testGetOne404() throws Exception {
        int id = 1;
        when(srv.getOne(id)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/peliculas/v1/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Not Found"))
                .andDo(print());
    }
   
    @Test
    void testCreateCorto() throws Exception {
        var filmEditDTO = new FilmEditDTO(0, "Description", 120, "PG-13", (short) 2022, (byte) 5, BigDecimal.valueOf(4.99), BigDecimal.valueOf(19.99), "New Film", 1, null);
        var film = FilmEditDTO.from(filmEditDTO);
        when(srv.add(any(Film.class))).thenReturn(film);

        mockMvc.perform(post("/api/peliculas/v1/corto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmEditDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(print());
    }

    @Test
    void testUpdate() throws Exception {
    	var filmEditDTO = new FilmEditDTO(1, "Updated Description", 130, "R", (short) 2023, (byte) 6, BigDecimal.valueOf(5.99), BigDecimal.valueOf(29.99), "Updated Film", 1, null);
        
        // Convertir FilmEditDTO a Film porque el método modify espera un Film
        var film = FilmEditDTO.from(filmEditDTO);

        when(srv.modify(any(Film.class))).thenReturn(film);

        mockMvc.perform(put("/api/peliculas/v1/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmEditDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }
    
    @Test
    void testUpdateIdsMismatch() throws Exception {
        var filmEditDTO = new FilmEditDTO(2, "Updated Description", 130, "R", (short) 2023, (byte) 6, BigDecimal.valueOf(5.99), BigDecimal.valueOf(29.99), "Updated Film", 1, null);

        mockMvc.perform(put("/api/peliculas/v1/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmEditDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @Test
    void testDelete() throws Exception {
        int id = 1;
        doNothing().when(srv).deleteById(id);
        mockMvc.perform(delete("/api/peliculas/v1/{id}", id))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void testGetPelis() throws Exception {
        int id = 1;
        var item = new Film(id, "Description", (short) 2020);
        when(srv.getOne(id)).thenReturn(Optional.of(item));
        mockMvc.perform(get("/api/peliculas/v1/{id}/pelis", id))
                .andExpect(status().isOk())
                .andDo(print());
    }
}