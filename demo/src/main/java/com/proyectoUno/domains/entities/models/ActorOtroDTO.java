package com.proyectoUno.domains.entities.models;

import java.io.Serializable;

public class ActorOtroDTO implements Serializable{
	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorOtroDTO(int id, String nombre, String apellidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
}
