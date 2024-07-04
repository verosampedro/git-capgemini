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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.catalogo.domains.contrats.services.ActorService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@Value
	static class ActorShortMock implements ActorShort {
		int id;
		String nombre;
	}
	
	@Test
	void testGetAllString() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Diego Lopez"),
		        		new ActorShortMock(3, "Marcos Dieguez")));
		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/actores/v1?modo=short").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<ActorShort> lista = new ArrayList<>(
		        Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
		        		new ActorShortMock(2, "Diego Lopez"),
		        		new ActorShortMock(3, "Marcos Dieguez")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var item = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(item));
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(id))
	        .andExpect(jsonPath("$.nombre").value(item.getFirstName()))
	        .andExpect(jsonPath("$.apellidos").value(item.getLastName()))
	        .andDo(print());
	}
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/actores/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
	    var item = new Actor(id, "Pepito", "Grillo");
	    when(srv.add(item)).thenReturn(item);

	    mockMvc.perform(post("/api/actores/v1/corto")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(ActorDTO.from(item)))
	        )
	        .andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/actores/v1/corto/" + item.getActorId()))
	        .andDo(print());
	}

	@Test
	void testUpdate() throws Exception {
		int id = 1;
		var item = new ActorDTO(id, "Pepito", "Grillo");
		when(srv.modify(any())).thenReturn(ActorDTO.from(item));
		mockMvc.perform(put("/api/actores/v1/{id}", id)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(item))
			)
			.andExpect(status().isNoContent())
	        .andDo(print());
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		doNothing().when(srv).deleteById(id);
		mockMvc.perform(delete("/api/actores/v1/{id}", id))
			.andExpect(status().isNoContent())
	        .andDo(print());
	}

	@Test
	void testGetPelis() throws Exception {
		int id = 1;
		var item = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(item));
		mockMvc.perform(get("/api/actores/v1/{id}/pelis", id))
			.andExpect(status().isOk())
	        .andDo(print());
	}

}