package com.catalogo.domains.contracts.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.catalogo.domains.entities.Category;

public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
	List<Category> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
}