package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.Entorno;
import com.example.ioc.Rango;
import com.example.ioc.Saluda;

@SpringBootApplication
public class Demo1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		
		// MÉTODO GET
		var item = dao.findById(201);
		if(item.isEmpty()) {
			System.err.println("No encontrado");
		} else {
			var actor = item.get();
			actor.setFirstName(actor.getFirstName().toUpperCase());
			System.out.println(item.get());
		} 
		
		dao.findAll().forEach(System.out::println);
		
		// MÉTODO DELETE
		// dao.deleteById(201);
		
		 // MÉTODO CREATE
		 /* var actor = new Actor(0, "Verónica", "Sampedro");
		 System.out.println(dao.save(actor)); */
	}

/*	@Autowired
//	@Qualifier("es")
	Saluda saluda;
	
	@Autowired
//	@Qualifier("en")
	Saluda saluda2;
	
	@Autowired
	Entorno entorno;
	
	@Autowired
	private Rango rango;

//	@Autowired(required = false)
//	SaludaEnImpl git-capgemini; */
	

}