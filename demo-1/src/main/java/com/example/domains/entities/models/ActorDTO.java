package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ActorDTO implements Serializable {
	private int actorId;
	private String firstName;
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
