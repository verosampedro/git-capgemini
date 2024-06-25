package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ActorDTO implements Serializable {
	
	@JsonProperty("id")
	private int actorId;
	
	@JsonProperty("nombre")
	private String firstName;
	
	@JsonProperty("apellidos")
	private String lastName;
	
	public static ActorDTO from(Actor actor ) {
		return new ActorDTO(
				actor.getActorId(),
				actor.getFirstName(),
				actor.getLastName());
	}
	
	public static Actor from(ActorDTO actor ) {
		return new Actor(
				actor.getActorId(),
				actor.getFirstName(),
				actor.getLastName());
	}
}
