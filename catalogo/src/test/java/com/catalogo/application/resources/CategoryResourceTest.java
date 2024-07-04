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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.catalogo.domains.contrats.services.CategoryService;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.FilmCategory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void testGetAll() throws Exception {
		List<Category> lista = new ArrayList<>(
		        Arrays.asList(new Category(1, "Action"),
		        		new Category(2, "Comedy"),
		        		new Category(3, "Drama")));
		when(srv.getAll()).thenReturn(lista);
		mockMvc.perform(get("/api/categorias/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
	    var item = new Category(id, "Action");
	    when(srv.getOne(id)).thenReturn(Optional.of(item));
	    mockMvc.perform(get("/api/categorias/v1/{id}", id))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.categoria").value(item.getName()))
	        .andDo(print());
	}

	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/categorias/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testGetPelis() throws Exception {
		int id = 1;
	    var category = Mockito.mock(Category.class);

	    var film1 = new Film(1, "Film1");
	    var film2 = new Film(2, "Film2");

	    var filmCategory1 = Mockito.mock(FilmCategory.class);
	    var filmCategory2 = Mockito.mock(FilmCategory.class);

	    when(filmCategory1.getFilm()).thenReturn(film1);
	    when(filmCategory2.getFilm()).thenReturn(film2);

	    var filmCategories = List.of(filmCategory1, filmCategory2);

	    when(srv.getOne(id)).thenReturn(Optional.of(category));
	    when(category.getFilmCategories()).thenReturn(filmCategories);

	    mockMvc.perform(get("/api/categorias/v1/{id}/peliculas", id))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.size()").value(filmCategories.size()))
	        .andExpect(jsonPath("$[0].id").value(film1.getFilmId()))
	        .andExpect(jsonPath("$[0].titulo").value(film1.getTitle()))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var item = new Category(id, "Action");
		when(srv.add(any(Category.class))).thenReturn(item);
		mockMvc.perform(post("/api/categorias/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(item))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/categorias/v1/" + id))
	        .andDo(print());
	}

	@Test
	void testUpdate() throws Exception {
		int id = 1;
		var item = new Category(id, "Action");
		when(srv.modify(any(Category.class))).thenReturn(item);
		mockMvc.perform(put("/api/categorias/v1/{id}", id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(item))
			)
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.categoria").value(item.getName()))
	        .andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		doNothing().when(srv).deleteById(id);
		mockMvc.perform(delete("/api/categorias/v1/{id}", id))
			.andExpect(status().isNoContent())
	        .andDo(print());
	}
}
