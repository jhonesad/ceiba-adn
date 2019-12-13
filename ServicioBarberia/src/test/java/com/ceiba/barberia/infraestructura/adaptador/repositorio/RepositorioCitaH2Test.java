package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import static org.junit.Assert.*;

import java.util.Date;
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
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.CitaRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.BarberoEntidadDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaEntidadDataBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositorioCitaH2Test {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CitaRepositorioJPA citaRepositorioJPA;
	
	@Autowired
	private BarberoRepositorioJPA barberoRepositorioJPA;
	
	private RepositorioCitaH2 repositorioCitaH2;
	
	@Before
	public void setUp() {
		repositorioCitaH2 = Mockito.spy(new RepositorioCitaH2(citaRepositorioJPA));
	}
	
	@Test
	public void crear() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder()
				.withId(barberoE.getId())
				.withNombre(barberoE.getNombre()).build();
		
		Cita cita = CitaDataBuilder.aCitaDataBuilder().withId(null)
				.withFecha(new Date())
				.withBarbero(barbero)
				.withCorteCabello(true)
				.withCorteBarba(true)
				.withLavado(true)
				.withNombreCliente("Cliente test1").build();
		
		cita = repositorioCitaH2.crear(cita);
		
		assertNotNull(cita.getId());
	}
	
	@Test
	public void retornar() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		entityManager.persist(CitaEntidadDataBuilder.aCitaDataBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE)
				.withCorteCabello(true)
				.withCorteBarba(true)
				.withLavado(true)
				.withNombreCliente("Cliente test1").build());
		
		entityManager.persist(CitaEntidadDataBuilder.aCitaDataBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE)
				.withCorteCabello(true)
				.withCorteBarba(false)
				.withLavado(true)
				.withNombreCliente("Cliente test2").build());
		
		List<Cita> citas = repositorioCitaH2.retornar();
		
		assertNotNull(citas);
		assertEquals(2, citas.size());
		citas.forEach(cita -> {
			assertNotNull(cita.getId());
			assertNotNull(cita.getBarbero().getId());
			assertEquals(barberoE.getId(), cita.getBarbero().getId());
		});
	}
	
	@Test
	public void retornarCitasBarbero() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test2").build());
		BarberoEntidad barberoE1 = barberoRepositorioJPA.findAll().get(0);
		BarberoEntidad barberoE2 = barberoRepositorioJPA.findAll().get(1);
		
		entityManager.persist(CitaEntidadDataBuilder.aCitaDataBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE1)
				.withCorteCabello(true)
				.withCorteBarba(true)
				.withLavado(true)
				.withNombreCliente("Cliente test1").build());
		
		entityManager.persist(CitaEntidadDataBuilder.aCitaDataBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE2)
				.withCorteCabello(true)
				.withCorteBarba(false)
				.withLavado(true)
				.withNombreCliente("Cliente test2").build());
		
		Mockito.doReturn(entityManager).when(repositorioCitaH2).getEntityManager();
		
		List<Cita> citas = repositorioCitaH2.retornar(barberoE2.getId());
		
		assertNotNull(citas);
		assertEquals(1, citas.size());
		assertNotNull(citas.get(0).getId());
		assertNotNull(citas.get(0).getBarbero().getId());
		assertEquals(barberoE2.getId(), citas.get(0).getBarbero().getId());
	}
}
