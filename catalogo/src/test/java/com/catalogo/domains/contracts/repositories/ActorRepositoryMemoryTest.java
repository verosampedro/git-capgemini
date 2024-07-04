package com.catalogo.domains.contracts.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.models.ActorDTO;


@DataJpaTest
class ActorRepositoryMemoryTest {
	@Autowired
	private TestEntityManager em;

	@Autowired
	ActorRepository dao;
	
	@MockBean
	FilmService frv;
	
	@MockBean
	FilmRepository fr;

	 @BeforeEach
	    void setUp() {
		 dao.deleteAll();
		    em.getEntityManager().createQuery("DELETE FROM Actor").executeUpdate();
		    em.getEntityManager().createNativeQuery("ALTER TABLE Actor ALTER COLUMN actor_id RESTART WITH 1").executeUpdate();
	    }

	    @Nested
	    @DisplayName("Test de ActorRepository")
	    class TestActorRepository {

	        @Test
	        void testFindTop5ByLastNameStartingWithOrderByFirstNameDesc() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<Actor> result = dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("D");

	            assertThat(result).hasSize(5);
	            assertThat(result).extracting(Actor::getFirstName)
	                .containsExactly("Pablo", "Maria", "Marcos", "Luis", "Covadonga");
	        }

	        @Test
	        void testFindTop5ByLastNameStartingWith() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<Actor> result = dao.findTop5ByLastNameStartingWith("D", Sort.by("firstName").descending());

	            assertThat(result).hasSize(5);
	            assertThat(result).extracting(Actor::getFirstName)
	                .containsExactly("Pablo", "Maria", "Marcos", "Luis", "Covadonga");
	        }

	        @Test
	        void testFindByActorIdGreaterThanEqual() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<Actor> result = dao.findByActorIdGreaterThanEqual(4);

	            assertThat(result).hasSize(4);
	            assertThat(result).extracting(Actor::getActorId)
	                .containsExactly(4, 5, 6, 7);
	        }

	        @Test
	        void testFindByJPQL() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<Actor> result = dao.findByJPQL(4);

	            assertThat(result).hasSize(4);
	            assertThat(result).extracting(Actor::getActorId)
	                .containsExactly(4, 5, 6, 7);
	        }

	        @Test
	        void testReadByActorIdGreaterThanEqual() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<ActorDTO> result = dao.readByActorIdGreaterThanEqual(4);

	            assertThat(result).hasSize(4);
	            assertThat(result).extracting(ActorDTO::getActorId)
	                .containsExactly(4, 5, 6, 7);
	        }

	        @Test
	        void testFindByActorIdGreaterThanEqualWithProjection() {
	            dao.save(new Actor(1, "Luis", "Dominguez"));
	            dao.save(new Actor(2, "Marcos", "Dominguez"));
	            dao.save(new Actor(3, "Clara", "Dominguez"));
	            dao.save(new Actor(4, "Sergio", "Lopez"));
	            dao.save(new Actor(5, "Pablo", "Dominguez"));
	            dao.save(new Actor(6, "Covadonga", "Dominguez"));
	            dao.save(new Actor(7, "Maria", "Dominguez"));

	            List<ActorDTO> result = dao.findByActorIdGreaterThanEqual(4, ActorDTO.class);

	            assertThat(result).hasSize(4);
	            assertThat(result).extracting(ActorDTO::getActorId)
	                .containsExactly(4, 5, 6, 7);
	        }
	    }
	
}