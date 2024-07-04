package com.proyectoUno.domains.entities.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyectoUno.domains.entities.Actor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/* Clase para la prueba de Lombok */
@Data
@AllArgsConstructor
public class ActorDTO implements Serializable{
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	private String firstName;
	@JsonProperty("apellidos")
	private String lastName;
	
	//Si tengo una entidad y necesito un DTO:
	public static ActorDTO from(Actor source) {
		return new ActorDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
	
	//Si tengo un DTO y necesito una entidad:
	public static Actor from(ActorDTO source) {
		return new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
	
}
