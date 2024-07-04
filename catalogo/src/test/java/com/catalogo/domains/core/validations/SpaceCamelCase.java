package com.catalogo.domains.core.validations;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator;

/* 
 * Clase para formatear cadenas.
 * Convierte a una nomenclatura en la que cada palabra en medio de la frase comienza con una letra mayúscula sin espacios
 */
public class SpaceCamelCase extends DisplayNameGenerator.Standard {
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return spaceCamelCase(super.generateDisplayNameForClass(testClass));
	}
	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return spaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass));
	}
	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return this.spaceCamelCase(testMethod.getName()) 
				+ DisplayNameGenerator.parameterTypesAsString(testMethod);
	}
	private String spaceCamelCase(String name) {
		StringBuilder result = new StringBuilder();
		result.append(Character.toUpperCase(name.charAt(0)));
		for(char c : name.substring(1).toCharArray())
			result.append(Character.isUpperCase(c) ? (" " + Character.toLowerCase(c)) : c);
		return result.toString();
	}
}