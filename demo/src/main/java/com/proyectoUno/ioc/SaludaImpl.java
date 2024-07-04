package com.proyectoUno.ioc;

import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component("saludaEs")
//@Qualifier("es")
@Scope("prototype")
@Profile("es")
public class SaludaImpl implements Saluda {
	//Clase estática para el evento
	/*
	public static class SaludaEvento {
		private String tipo;
		private String destinatario;
		
		public SaludaEvento(String tipo, String destinatario) {
			this.tipo = tipo;
			this.destinatario = destinatario;
		}
		public String tipo() {
			return tipo;
		}
		public String destinatario() {
			return destinatario;
		}
	}
	*/
	
	//Record para el evento --> con esta linea se sustituye todo lo anterior, ya no hace falta
	public static record saludaEvent(String tipo, String destinatario) { }
	
	private ApplicationEventPublisher publisher;
	private Entorno entorno;
	
	public SaludaImpl(Entorno entorno, ApplicationEventPublisher publisher) {
		this.entorno = entorno;
		this.publisher = publisher;
	}

	/* Publica el evento */
	protected void onSaluda(@NonNull String tipo, @NonNull String destinatario) {
		publisher.publishEvent(new saludaEvent(tipo, destinatario));
	}
	
	@Override
	public void saluda(@NonNull String nombre) {
//		if(nombre == null)
//			throw new IllegalArgumentException("El nombre es obligatorio.");
		entorno.write("Hola " + nombre.toUpperCase());
		onSaluda("saluda",nombre);
	}
	public void saluda() {
		entorno.write("Hola");
		onSaluda("saluda ","sin nombre");
	}

	@Override
	public int getContador() {
		return entorno.getContador();
	}

	
	public Optional<Entorno> getEntorno() {
		return Optional.ofNullable(entorno);
	}
}
