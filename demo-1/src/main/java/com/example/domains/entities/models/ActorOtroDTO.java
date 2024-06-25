package com.example.domains.entities.models;

import java.io.Serializable;

public class ActorOtroDTO implements Serializable {

	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorOtroDTO(int actorId, String firstName, String lastName) {
		this.id = actorId;
		this.nombre = firstName;
		this.apellidos = lastName;
	}
}
