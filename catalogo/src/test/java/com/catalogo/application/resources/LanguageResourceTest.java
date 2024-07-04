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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.catalogo.domains.contrats.services.LanguageService;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.LanguageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private LanguageService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void testGetAll() throws Exception {
		List<Language> lista = new ArrayList<>(
		        Arrays.asList(new Language(1, "English"),
		        		new Language(2, "Spanish"),
		        		new Language(3, "French")));
		when(srv.getAll()).thenReturn(lista);
		mockMvc.perform(get("/api/lenguajes/v1").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var item = new Language(id, "English");
		when(srv.getOne(id)).thenReturn(Optional.of(item));
		mockMvc.perform(get("/api/lenguajes/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.languageId").value(id))
	        .andExpect(jsonPath("$.name").value(item.getName()))
	        .andDo(print());
	}

	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/lenguajes/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testGetOneCorto() throws Exception {
		int id = 1;
		var item = new Language(id, "English");
		when(srv.getOne(id)).thenReturn(Optional.of(item));
		mockMvc.perform(get("/api/lenguajes/v1/{id}/V2", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.languageId").value(id))
	        .andExpect(jsonPath("$.name").value(item.getName()))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var item = new LanguageDTO(id, "English");
		when(srv.add(any(Language.class))).thenReturn(LanguageDTO.from(item));
		mockMvc.perform(post("/api/lenguajes/v1/corto")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(item))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/lenguajes/v1/corto/" + id))
	        .andDo(print());
	}

	@Test
	void testUpdate() throws Exception {
	    int id = 1;
	    var item = new LanguageDTO(id, "English");
	    when(srv.modify(any(Language.class))).thenReturn(LanguageDTO.from(item));
	    
	    mockMvc.perform(put("/api/lenguajes/v1/{id}", id)
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(item)))
	        .andExpect(status().isOk())
	        .andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		doNothing().when(srv).deleteById(id);
		mockMvc.perform(delete("/api/lenguajes/v1/{id}", id))
			.andExpect(status().isNoContent())
	        .andDo(print());
	}
}
