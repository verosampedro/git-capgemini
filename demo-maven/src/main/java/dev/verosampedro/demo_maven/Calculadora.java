package dev.verosampedro.demo_maven;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {

	private double redondeo(double o) {
		return (new BigDecimal(o)).setScale(16, RoundingMode.HALF_UP).doubleValue();
	}
	
	double add(double a, double b) {
		return redondeo(a+b);
	}
	
	double div(double a, double b) {
		
		if(b == 0) throw new ArithmeticException(" / by 0");
		
		return redondeo(a/b);
	}
	
	double multiply(double a, double b) {
		return redondeo(a*b);
	}
}
