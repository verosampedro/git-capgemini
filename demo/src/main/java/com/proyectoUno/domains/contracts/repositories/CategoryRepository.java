package com.proyectoUno.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.proyectoUno.domains.entities.Category;

@RepositoryRestResource(path="categorias", itemResourceRel="categoria", collectionResourceRel="categorias")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
