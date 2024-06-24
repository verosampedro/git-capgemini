package dev.verosampedro.demo_maven;

public class PersonaService {

	private PersonaRepository dao;

	public PersonaService(PersonaRepository dao) {
		super();
		this.dao = dao;
	}
	
	public void upperCase(int id) {
		var item = dao.getOne(id);
		if(item.isEmpty())
			throw new IllegalArgumentException("El ID no ha sido encontrado");
		
		var p = item.get();
		p.setNombre(p.getNombre().toUpperCase());
		
		dao.modify(p);
	}
}
