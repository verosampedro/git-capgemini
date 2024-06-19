package dev.verosampedro.demo_maven;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAdd() {
		
		Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.add(1, 2);
		
		assertEquals(3, resultado);
	}
	
	@Test
	void testAdd2() {
		
		Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.add(0.1, 0.2);
		
		assertEquals(0.3, resultado);
	}
	
	@Test
	void testDivInt() {
		
		Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.div(3, 2);
		
		assertEquals(1.5, resultado);
	}
	
	@Test
	void testDivRealOK() {
		
		Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.div(3.0, 2.0);
		
		assertEquals(1.5, resultado);
	}
	
	@Test
	void testDivRealKO() {
		
		Calculadora calculadora = new Calculadora();
		
		assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
		
	}

}
