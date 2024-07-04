package com.proyectoUno;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.proyectoUno.ioc.Saluda;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {
	/* 
	 Suplantamos el contexto de inyección con los datos que queremos mockear.
	 De modo que en los test de abajo:
	 -Si tenemos el @TestConfiguration class Contexto, inyectará los datos que hemos mockeado
	 -Si no la tenemos, cogerá los datos reales
	 */
	@TestConfiguration
	static class Contexto {
		@Bean
		Saluda saluda() {
			var simula = mock(Saluda.class);
			when(simula.getContador()).thenReturn(666);
			return simula;
		}
	}
	
	@Autowired
	Saluda saluda;
	
	@Test
	void contextLoads() {
		assertEquals(666, saluda.getContador());
	}

}
