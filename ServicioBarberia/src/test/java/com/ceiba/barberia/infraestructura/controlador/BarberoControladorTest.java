package com.ceiba.barberia.infraestructura.controlador;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorCrearBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarBarberos;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.repositorio.RepositorioBarberoH2;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidadDataBuilder;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BarberoControladorTest extends ControladorText {
	
	private BarberoControlador barberoControlador;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private BarberoRepositorioJPA barberoRepositorioJPA;
	
	private RepositorioBarberoH2 repositorioBarberoH2;
	private ServicioBarbero servicioBarbero;
	private FabricaBarbero fabricaBarbero;
	private ManejadorListarBarberos manejadorListarBarberos;
	private ManejadorCrearBarbero manejadorCrearBarbero;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		repositorioBarberoH2 = Mockito.spy(new RepositorioBarberoH2(barberoRepositorioJPA)); 
		fabricaBarbero = Mockito.spy(new FabricaBarbero());
		servicioBarbero = Mockito.spy(new ServicioBarbero(repositorioBarberoH2));
		manejadorListarBarberos = Mockito.spy(new ManejadorListarBarberos(servicioBarbero, fabricaBarbero));
		manejadorCrearBarbero = Mockito.spy(new ManejadorCrearBarbero(servicioBarbero, fabricaBarbero));
		
		barberoControlador = new BarberoControlador(manejadorListarBarberos, manejadorCrearBarbero);
		mockMvc = MockMvcBuilders.standaloneSetup(barberoControlador).build();
	}
	
	@Test
	public void listarBarberos() throws Exception {
		BarberoEntidad barbero1 = BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test barbero 1").build();
		BarberoEntidad barbero2 = BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test barbero 2").build();
		entityManager.persist(barbero1);
		entityManager.persist(barbero2);
		
		MvcResult result = mockMvc.perform(get("/api/barbero/listar")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andReturn();
		
		Mockito.verify(manejadorListarBarberos, Mockito.times(1)).ejecutar();
		Mockito.verify(servicioBarbero, Mockito.times(1)).listar();
		Mockito.verify(repositorioBarberoH2, Mockito.times(1)).listar();
		Mockito.verify(fabricaBarbero, Mockito.times(2)).barbero(Mockito.any(Barbero.class));
		
		List<ComandoBarbero> barberos = Arrays.asList(mapper.readValue(result.getResponse().getContentAsString(), ComandoBarbero[].class));
		barberos.forEach(bar -> {
			assertNotNull(bar.getId());
		});
		assertTrue(barberos.stream().anyMatch(bar -> barbero1.getNombre().equals(bar.getNombre())));
		assertTrue(barberos.stream().anyMatch(bar -> barbero2.getNombre().equals(bar.getNombre())));
	}
	
	@Test
	public void crearBarbero() throws Exception {
		String nombreBarbero = "Jhones Test";
		ComandoBarbero barberoMock = ComandoBarbero.builder().id(0l).nombre(nombreBarbero).build();
		Mockito.doReturn(entityManager).when(repositorioBarberoH2).getEntityManager();
		
		MvcResult result = mockMvc.perform(post("/api/barbero/crear")
				.content(asJsonString(barberoMock))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andReturn();
		
		Mockito.verify(manejadorCrearBarbero, Mockito.times(1)).ejecutar(Mockito.any(ComandoBarbero.class));
		Mockito.verify(fabricaBarbero, Mockito.times(1)).crear(Mockito.any(ComandoBarbero.class));
		Mockito.verify(servicioBarbero, Mockito.times(1)).crear(Mockito.any(Barbero.class));
		Mockito.verify(repositorioBarberoH2, Mockito.times(1)).listarPorNombre(nombreBarbero);
		Mockito.verify(repositorioBarberoH2, Mockito.times(1)).crear(Mockito.any(Barbero.class));
		
		ComandoBarbero barbero = mapper.readValue(result.getResponse().getContentAsString(), ComandoBarbero.class);
		assertNotNull(barbero.getId());
		
		List<BarberoEntidad> barberosBD = barberoRepositorioJPA.findAll();
		assertNotNull(barberosBD);
		assertEquals(1, barberosBD.size());
		assertEquals(barbero.getId(), barberosBD.get(0).getId());
		assertEquals(nombreBarbero, barberosBD.get(0).getNombre());
	}
}
