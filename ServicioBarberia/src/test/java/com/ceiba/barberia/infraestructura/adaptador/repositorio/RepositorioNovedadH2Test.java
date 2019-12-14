package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.NovedadRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.NovedadEntidad;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.BarberoEntidadDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadEntidadDataBuilder;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositorioNovedadH2Test {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private NovedadRepositorioJPA novedadRepositorioJPA;
	
	@Autowired
	private BarberoRepositorioJPA barberoRepositorioJPA;
	
	private RepositorioNovedadH2 repositorioNovedadH2;
	
	@Before
	public void setUp() {
		repositorioNovedadH2 = Mockito.spy(new RepositorioNovedadH2(novedadRepositorioJPA));
	}
	
	@Test
	public void crear() {	
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder()
				.withId(barberoE.getId())
				.withNombre(barberoE.getNombre()).build();
		
		Novedad novedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withBarbero(barbero)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withFestivo(false)
				.withDescripcion("Nueva novedad test1").build();
				
		novedad = repositorioNovedadH2.crear(novedad);
		
		assertNotNull(novedad.getId());
	}
	
	@Test
	public void retornar() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(barberoE)
				.withDescripcion("novedad test1")
				.withFestivo(false).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(barberoE)
				.withDescripcion("novedad test2")
				.withFestivo(false).build());
		
		List<Novedad> novedades = repositorioNovedadH2.retornar();
		
		assertNotNull(novedades);
		assertEquals(2, novedades.size());
		novedades.forEach(novedad -> {
			assertNotNull(novedad.getId());
			assertNotNull(novedad.getBarbero().getId());
			assertEquals(barberoE.getId(), novedad.getBarbero().getId());
		});
	}
	
	@Test
	public void listarPorBarbero() {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test2").build());
		BarberoEntidad barberoE1 = barberoRepositorioJPA.findAll().get(0);
		BarberoEntidad barberoE2 = barberoRepositorioJPA.findAll().get(1);
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(barberoE1)
				.withDescripcion("novedad test1")
				.withFestivo(false).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(null)
				.withDescripcion("novedad festivo1")
				.withFestivo(true).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(barberoE2)
				.withDescripcion("novedad test2")
				.withFestivo(false).build());
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		List<Novedad> novedades = repositorioNovedadH2.listarPorBarbero(barberoE2.getId());
		
		assertNotNull(novedades);
		assertEquals(1, novedades.size());
		assertNotNull(novedades.get(0).getId());
		assertNotNull(novedades.get(0).getBarbero().getId());
		assertEquals(barberoE2.getId(), novedades.get(0).getBarbero().getId());
	}
	
	private void crearModeloFestivosTest() throws Exception {
		entityManager.persist(BarberoEntidadDataBuilder.aBarberoDataBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE1 = barberoRepositorioJPA.findAll().get(0);
		
		SimpleDateFormat formatterBD = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withBarbero(barberoE1)
				.withDescripcion("novedad test1")
				.withFestivo(false).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(formatterBD.parse("20191208 00:00"))
				.withFechaFin(formatterBD.parse("20191208 23:59"))
				.withBarbero(null)
				.withDescripcion("novedad test dia velitas")
				.withFestivo(true).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(formatterBD.parse("20191225 00:00"))
				.withFechaFin(formatterBD.parse("20191225 23:59"))
				.withBarbero(null)
				.withDescripcion("novedad test navidad")
				.withFestivo(true).build());
		
		entityManager.persist(NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withFechaInicio(formatterBD.parse("20200101 00:00"))
				.withFechaFin(formatterBD.parse("20200101 23:59"))
				.withBarbero(null)
				.withDescripcion("novedad test anio nuevo")
				.withFestivo(true).build());
	}
	
	@Test
	public void listarFestivosConFechaAnterior() throws Exception {

		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20191207 23:59:59");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		List<Novedad> novedades = repositorioNovedadH2.listarFestivos(fechaConsulta);
		
		assertNotNull(novedades);
		assertEquals(3, novedades.size());
		novedades.forEach(nov -> {
			assertNotNull(nov.getId());
			assertNull(nov.getBarbero());
			assertTrue(nov.getFestivo());
		});
	}
	
	@Test
	public void listarFestivosConFechaMismoDia() throws Exception {

		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20191208 14:00:00");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		List<Novedad> novedades = repositorioNovedadH2.listarFestivos(fechaConsulta);
		
		assertNotNull(novedades);
		assertEquals(3, novedades.size());
		novedades.forEach(nov -> {
			assertNotNull(nov.getId());
			assertNull(nov.getBarbero());
			assertTrue(nov.getFestivo());
		});
	}
	
	@Test
	public void listarFestivosConFechaMismoDiaUltimoMomento() throws Exception {

		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20191225 23:59:59");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		List<Novedad> novedades = repositorioNovedadH2.listarFestivos(fechaConsulta);
		
		assertNotNull(novedades);
		assertEquals(2, novedades.size());
		novedades.forEach(nov -> {
			assertNotNull(nov.getId());
			assertNull(nov.getBarbero());
			assertTrue(nov.getFestivo());
		});
	}
	
	@Test
	public void listarFestivosConFechaFutura() throws Exception {

		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20200102 00:00:00");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		List<Novedad> novedades = repositorioNovedadH2.listarFestivos(fechaConsulta);
		
		assertNotNull(novedades);
		assertEquals(0, novedades.size());
	}
	
	@Test
	public void consultarFestivoConFechaFestivo() throws Exception {
		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20191225 15:07:45");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		Novedad festivo = repositorioNovedadH2.consultarFestivo(fechaConsulta);
		
		assertNotNull(festivo);
	}
	
	@Test
	public void consultarFestivoNoRegistrado() throws Exception {
		crearModeloFestivosTest();
		Date fechaConsulta = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("20191220 09:45:03");
		
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		Novedad festivo = repositorioNovedadH2.consultarFestivo(fechaConsulta);
		
		assertNull(festivo);
	}
	
	@Test
	public void listaNovedadIsNull() {
		boolean validacion = repositorioNovedadH2.listaNovedadIsNullEmpty(null);
		assertTrue(validacion);
	}
	
	@Test
	public void listaNovedadIsEmpty() {
		List<NovedadEntidad> lista = new ArrayList<>();
		boolean validacion = repositorioNovedadH2.listaNovedadIsNullEmpty(lista);
		assertTrue(validacion);
	}
	
	@Test
	public void listaNovedadIsNotNullEmpty() {
		NovedadEntidad aNovedad = NovedadEntidadDataBuilder.aNovedadDataBuilder()
				.withId(1l)
				.withBarbero(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withFestivo(true)
				.withDescripcion("test").build();
		List<NovedadEntidad> lista = new ArrayList<>();
		lista.add(aNovedad);
		boolean validacion = repositorioNovedadH2.listaNovedadIsNullEmpty(lista);
		assertFalse(validacion);
	}
}
