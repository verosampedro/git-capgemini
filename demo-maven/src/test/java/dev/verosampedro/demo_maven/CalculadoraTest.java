package dev.verosampedro.demo_maven;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {

	Calculadora calculadora;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calculadora = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Método Add")
	class Add {
	
		@Nested 
		class OK {
			
	@Test
	@DisplayName("Suma dos enteros")
	void testAdd() {
		
		// Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.add(1, 2);
		
		assertEquals(3, resultado);
	}
	
	@Test
	@DisplayName("Suma dos decimales")
	void testAdd2() {
		
		// Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.add(0.1, 0.2);
		
		assertEquals(0.3, resultado);
	}	
		}
		
		@Nested 
		class KO {
			
		}
	}

	
	
	@Nested
	@DisplayName("Método Div")
	class Div {
	
		@Nested 
		class OK {
			
	@Test
	@DisplayName("Divide dos enteros")
	void testDivInt() {
			
		// Calculadora calculadora = new Calculadora();
			
		double resultado = calculadora.div(3, 2);
			
		assertEquals(1.5, resultado);
	}
	
	@Test
	@DisplayName("Divide dos reales")
	void testDivRealOK() {
		
		// Calculadora calculadora = new Calculadora();
		
		double resultado = calculadora.div(3.0, 2.0);
		
		assertEquals(1.5, resultado);
	}	
		}
		
		@Nested 
		class KO {
			
	@Test
	@DisplayName("División por 0")
	void testDivRealKO() {
		
		// Calculadora calculadora = new Calculadora();
		
		assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
		
		}	
	}
		
	}
	
	
	
	

}
