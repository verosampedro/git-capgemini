package com.catalogo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
	}
}
