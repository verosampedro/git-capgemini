package com.catalogo.domains.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.catalogo.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor extends EntityBase<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Actor(int actorId) {
		super();
		this.actorId = actorId;
	}

	public Actor(int actorId, String firstName, String lastName) {
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false, length=5)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	@NotBlank //No puede estar en blanco
	@Size(max=45, min=2) //Tamaño máximo de 45 dígitos
	@Pattern(regexp = "[a-zA-Z ]+", message = "tienen que ser letras") //Para estipular una expresión regular para validar el campo
	private String firstName;

	@Column(name="last_name", nullable=false, length=45)
	@NotBlank
	@Size(max=45, min=2)
	@Pattern(regexp = "[a-zA-Z ]+", message = "tienen que ser letras")
	private String lastName;

	@Column(name="last_update", /*nullable=false,*/ updatable=false, insertable=false)
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy="actor") 
//	@OneToMany(mappedBy="actor", fetch = FetchType.EAGER) //Forma 1 - para mantener la conexión abierta y que traiga toda la información de las películas siempre
//	Si ponemos el fetch = FetchType.EAGER no es necesario el @Transactional en la DemoApplication.java
// 	La otra opción sería FetchType.LAZY que sería perezosa --> por defecto se aplica esa si no ponemos nada
	//2 formas para que cuando serialice el actor, no serialice todas sus peliculas:
//	@JsonIgnore //Forma 1 - Para que los ignore
	@JsonBackReference //Forma 2 - Para que atienda al 	@JsonManagedReference de la clase FilmActor y el resultado será el mismo
	private List<FilmActor> filmActors;

	public Actor() {
	}

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return filmActors != null ? filmActors : new ArrayList<>();
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

	public void recibePremio(String premio) {
		
	}
}