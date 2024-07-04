package com.catalogo.application.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogo.application.contracts.CatalogoService;
import com.catalogo.domains.contrats.services.ActorService;
import com.catalogo.domains.contrats.services.CategoryService;
import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.contrats.services.LanguageService;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.domains.entities.models.NovedadesDTO;

@Service
public class CatalogoServiceImpl implements CatalogoService {
	@Autowired
	private FilmService filmSrv;
	@Autowired
	private ActorService artorSrv;
	@Autowired
	private CategoryService categorySrv;
	@Autowired
	private LanguageService languageSrv;

	@Override
	public NovedadesDTO novedades(Timestamp fecha) {
		// Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
		if(fecha == null)
			fecha = Timestamp.from(Instant.now().minusSeconds(36000));
		return new NovedadesDTO(
				filmSrv.novedades(fecha).stream().map(item -> new FilmShortDTO(item.getFilmId(), item.getTitle())).toList(), 
				artorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
				categorySrv.novedades(fecha), 
				languageSrv.novedades(fecha)
				);
	}

}