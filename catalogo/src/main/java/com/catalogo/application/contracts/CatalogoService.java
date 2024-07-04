package com.catalogo.application.contracts;

import java.sql.Timestamp;

import com.catalogo.domains.entities.models.NovedadesDTO;


public interface CatalogoService {

	NovedadesDTO novedades(Timestamp fecha);

}