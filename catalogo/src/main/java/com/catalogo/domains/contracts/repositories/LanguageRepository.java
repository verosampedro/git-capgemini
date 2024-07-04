package com.catalogo.domains.contracts.repositories;


import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import com.catalogo.domains.entities.Language;

public interface LanguageRepository extends ListCrudRepository<Language, Integer>{
	List<Language>findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	@Query(value = "SELECT count(*) FROM Language l")
	int cuentaLenguajes();
}
