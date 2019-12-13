package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.BarberoEntidadDataBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositorioBarberoH2Test {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private BarberoRepositorioJPA barberoRepositorioJPA;
	
	private RepositorioBarberoH2 repositorioBarberoH2;
	
	@Before
	public void setUp() {
		repositorioBarberoH2 = Mockito.spy(new RepositorioBarberoH2(barberoRepositorioJPA)); 
	}
	
	@Test
	public void listar() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test2").build());
		
		List<Barbero> barberos = repositorioBarberoH2.listar();
		
		assertNotNull(barberos);
		assertEquals(2, barberos.size());
		barberos.forEach(barbero -> {
			assertNotNull(barbero.getId());
		});
	}
	
	@Test
	public void crear() {
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().withId(null).build();
		
		barbero = repositorioBarberoH2.crear(barbero);
		
		assertNotNull(barbero.getId());
	}
	
	@Test
	public void listarPorNombre() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("jhon david").build());
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("david alejandro").build());
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("juan camilo").build());
		
		Mockito.doReturn(entityManager).when(repositorioBarberoH2).getEntityManager();
		
		String nombre = "dav";
		
		List<Barbero> barberos = repositorioBarberoH2.listarPorNombre(nombre);
		
		assertNotNull(barberos);
		assertEquals(2, barberos.size());
		barberos.forEach(barbero -> {
			assertNotNull(barbero.getId());
		});
	}
}
