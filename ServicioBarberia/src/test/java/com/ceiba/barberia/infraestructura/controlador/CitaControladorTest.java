package com.ceiba.barberia.infraestructura.controlador;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorAgendarCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarCitas;
import com.ceiba.barberia.dominio.servicio.ServicioCita;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.CitaRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.NovedadRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.repositorio.RepositorioCitaH2;
import com.ceiba.barberia.infraestructura.adaptador.repositorio.RepositorioNovedadH2;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidadDataBuilder;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidad;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidadDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CitaControladorTest extends ControladorText {
	
	private CitaControlador citaControlador;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private CitaRepositorioJPA citaRepositorioJPA;
	
	@Autowired
	private NovedadRepositorioJPA novedadRepositorioJPA;
	
	@Autowired
	private BarberoRepositorioJPA barberoRepositorioJPA;
	
	private RepositorioCitaH2 repositorioCitaH2;
	private RepositorioNovedadH2 repositorioNovedadH2;
	private ServicioCita servicioCita;
	private FabricaCita fabricaCita;
	private ManejadorListarCitas manejadorListarCitas;
	private ManejadorAgendarCita manejadorAgendarCita;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		repositorioCitaH2 = Mockito.spy(new RepositorioCitaH2(citaRepositorioJPA));		
		repositorioNovedadH2 = Mockito.spy(new RepositorioNovedadH2(novedadRepositorioJPA));
		Mockito.doReturn(entityManager).when(repositorioCitaH2).getEntityManager();
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		servicioCita = new ServicioCita(repositorioCitaH2, repositorioNovedadH2);
		fabricaCita = new FabricaCita();
		manejadorListarCitas = new ManejadorListarCitas(servicioCita, fabricaCita);
		manejadorAgendarCita = new ManejadorAgendarCita(servicioCita, fabricaCita);
		citaControlador = new CitaControlador(manejadorListarCitas, manejadorAgendarCita);
		
		mockMvc = MockMvcBuilders.standaloneSetup(citaControlador).build();
	}
	
	@Test
	public void listarCitas() throws Exception {
		entityManager.persist(BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		CitaEntidad cita1 = CitaEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE)
				.withCorteCabello(true)
				.withCorteBarba(true)
				.withLavado(true)
				.withNombreCliente("Cliente test1").build();
		CitaEntidad cita2 = CitaEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFecha(new Date())
				.withBarbero(barberoE)
				.withCorteCabello(true)
				.withCorteBarba(false)
				.withLavado(true)
				.withNombreCliente("Cliente test2").build();
		
		entityManager.persist(cita1);
		entityManager.persist(cita2);		
		
		mockMvc.perform(get("/api/cita/listar")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].barbero.id").value(barberoE.getId()))
			.andExpect(jsonPath("$.[0].id").isNotEmpty())
			.andExpect(jsonPath("$.[1].id").isNotEmpty())
			.andExpect(jsonPath("$.[1].barbero.id").value(barberoE.getId()));
	}
	
	@Test
	public void agendarCita() throws Exception {
		entityManager.persist(BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);		
		Calendar calendar = Calendar.getInstance();
		Date today = new Date();
		calendar.setTime(today);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		
		ComandoCita citaCrear = ComandoCita.builder()
				.id(0l)
				.fecha(calendar.getTime())
				.barbero(ComandoBarbero.builder().id(barberoE.getId()).nombre(barberoE.getNombre()).build())
				.corteCabello(true)
				.corteBarba(true)
				.lavado(true)
				.nombreCliente("Cliente test1").build();
		
		MvcResult result = mockMvc.perform(post("/api/cita/agendar")
				.content(asJsonString(citaCrear))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andReturn();
		
		ComandoCita cita = mapper.readValue(result.getResponse().getContentAsString(), ComandoCita.class);
		assertNotNull(cita);
		assertEquals(citaCrear.getBarbero().getId(), cita.getBarbero().getId());
		assertEquals(citaCrear.getNombreCliente(), cita.getNombreCliente());
	}
}
