package com.proyectoUno.domains.core.contracts.services;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;

import com.proyectoUno.exceptions.InvalidDataException;
import com.proyectoUno.exceptions.NotFoundException;

//Contiene las operaciones CRUD generales
public interface DomainService<E, K> {
	List<E> getAll();
	
	Optional<E> getOne(K id);
	
	E add(E item) throws DuplicateKeyException, InvalidDataException;
	
	E modify(E item) throws NotFoundException, InvalidDataException;
	
	void delete(E item) throws InvalidDataException;
	void deleteById(K id);
}