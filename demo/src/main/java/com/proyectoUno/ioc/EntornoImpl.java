package com.proyectoUno.ioc;

//@Service
public class EntornoImpl implements Entorno {
	private int contador;
	
	public EntornoImpl(int contInit) {
		this.contador = contInit;
	}

	@Override
	public void write(String cad) {
		contador++;
		System.out.println(cad);
	}

	public int getContador() {
		return contador;
	}
	
}