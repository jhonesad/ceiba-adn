package com.ceiba.barberia.infraestructura.controlador;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
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
import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorCrearNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarFestivos;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarNovedades;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.CitaRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.NovedadRepositorioJPA;
import com.ceiba.barberia.infraestructura.adaptador.repositorio.RepositorioCitaH2;
import com.ceiba.barberia.infraestructura.adaptador.repositorio.RepositorioNovedadH2;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidadDataBuilder;
import com.ceiba.barberia.infraestructura.entidad.NovedadEntidad;
import com.ceiba.barberia.infraestructura.entidad.NovedadEntidadDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NovedadControladorTest extends ControladorText {
	
	private NovedadControlador novedadControlador;
	
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
	private ServicioNovedad servicioNovedad;
	private FabricaNovedad fabricaNovedad;
	
	private ManejadorCrearNovedad manejadorCrearNovedad;
	private ManejadorListarNovedades manejadorListarNovedades;
	private ManejadorListarFestivos manejadorListarFestivos;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() {
		repositorioCitaH2 = Mockito.spy(new RepositorioCitaH2(citaRepositorioJPA));		
		repositorioNovedadH2 = Mockito.spy(new RepositorioNovedadH2(novedadRepositorioJPA));
		Mockito.doReturn(entityManager).when(repositorioCitaH2).getEntityManager();
		Mockito.doReturn(entityManager).when(repositorioNovedadH2).getEntityManager();
		
		servicioNovedad = new ServicioNovedad(repositorioNovedadH2, repositorioCitaH2);
		fabricaNovedad = new FabricaNovedad();
		manejadorListarNovedades = new ManejadorListarNovedades(servicioNovedad, fabricaNovedad);
		manejadorCrearNovedad = new ManejadorCrearNovedad(servicioNovedad, fabricaNovedad);
		manejadorListarFestivos = new ManejadorListarFestivos(servicioNovedad, fabricaNovedad);
		novedadControlador = Mockito.spy(new NovedadControlador(manejadorCrearNovedad, manejadorListarNovedades, manejadorListarFestivos));
		
		mockMvc = MockMvcBuilders.standaloneSetup(novedadControlador).build();
	}
	
	@Test
	public void listarNovedades() throws Exception {
		entityManager.persist(BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		NovedadEntidad novedad = NovedadEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFestivo(false)
				.withBarbero(barberoE)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withDescripcion("Novedad test barbero")
				.build();
		
		NovedadEntidad festivo = NovedadEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFestivo(true)
				.withBarbero(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withDescripcion("Novedad test festivo")
				.build();
		
		entityManager.persist(novedad);
		entityManager.persist(festivo);	
		
		mockMvc.perform(get("/api/novedad/listar")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$.[0].id").isNotEmpty())
			.andExpect(jsonPath("$.[1].id").isNotEmpty());
	}
	
	@Test
	public void crearNovedadBarbero() throws Exception {
		entityManager.persist(BarberoEntidadDataBuilder.aBuilder().withId(null).withNombre("test1").build());
		BarberoEntidad barberoE = barberoRepositorioJPA.findAll().get(0);
		
		Date today = new Date();
		
		Calendar calendarFI = Calendar.getInstance();
		calendarFI.setTime(today);
		calendarFI.add(Calendar.DAY_OF_MONTH, 1);
		calendarFI.set(Calendar.HOUR_OF_DAY, 8);
		calendarFI.set(Calendar.MINUTE, 0);
		calendarFI.set(Calendar.SECOND, 0);
		calendarFI.set(Calendar.MILLISECOND, 0);
		
		Calendar calendarFF = Calendar.getInstance();
		calendarFF.setTime(today);
		calendarFF.add(Calendar.DAY_OF_MONTH, 1);
		calendarFF.set(Calendar.HOUR_OF_DAY, 10);
		calendarFF.set(Calendar.MINUTE, 0);
		calendarFF.set(Calendar.SECOND, 0);
		calendarFF.set(Calendar.MILLISECOND, 0);
		
		ComandoNovedad novedadGuardar = ComandoNovedad.builder()
				.id(0l)
				.festivo(false)
				.barbero(ComandoBarbero.builder().id(barberoE.getId()).nombre(barberoE.getNombre()).build())
				.fechaInicio(calendarFI.getTime())
				.fechaFin(calendarFF.getTime())
				.descripcion("test incapacidad")
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/novedad/crear")
				.content(asJsonString(novedadGuardar))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andReturn();
		
		ComandoNovedad novedad = mapper.readValue(result.getResponse().getContentAsString(), ComandoNovedad.class);
		assertNotNull(novedad);
		assertEquals(novedadGuardar.getBarbero().getId(), novedad.getBarbero().getId());
		assertEquals(novedadGuardar.getDescripcion(), novedad.getDescripcion());
	}
	
	@Test
	public void crearNovedadFestivo() throws Exception {
		Date today = new Date();
		
		Calendar calendarF = Calendar.getInstance();
		calendarF.setTime(today);
		calendarF.add(Calendar.DAY_OF_MONTH, 1);
		
		ComandoNovedad novedadGuardar = ComandoNovedad.builder()
				.id(0l)
				.festivo(true)
				.barbero(null)
				.fechaInicio(calendarF.getTime())
				.fechaFin(calendarF.getTime())
				.descripcion("test festivo")
				.build();
		
		MvcResult result = mockMvc.perform(post("/api/novedad/crear")
				.content(asJsonString(novedadGuardar))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").isNotEmpty())
			.andReturn();
		
		ComandoNovedad novedad = mapper.readValue(result.getResponse().getContentAsString(), ComandoNovedad.class);
		assertNotNull(novedad);
		assertNull(novedad.getBarbero());
		assertEquals(novedadGuardar.getDescripcion(), novedad.getDescripcion());
	}
	
	@Test
	public void listarFestivos() throws Exception {
		String fechaConsulta = "16-12-2019";		
		
		NovedadEntidad festivo1 = NovedadEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFestivo(true)
				.withBarbero(null)
				.withFechaInicio(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("08-12-2019 00:00:00"))
				.withFechaFin(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("08-12-2019 23:59:59"))
				.withDescripcion("Novedad test dia velitas")
				.build();
		
		NovedadEntidad festivo2 = NovedadEntidadDataBuilder.aBuilder()
				.withId(null)
				.withFestivo(true)
				.withBarbero(null)
				.withFechaInicio(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("25-12-2019 00:00:00"))
				.withFechaFin(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("25-12-2019 23:59:59"))
				.withDescripcion("Novedad test navidad")
				.build();
		
		entityManager.persist(festivo1);
		entityManager.persist(festivo2);
		
		mockMvc.perform(get("/api/novedad/listar-festivos/" + fechaConsulta)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$.[0].descripcion").value("Novedad test navidad"));
	}
	
	@Test
	public void listarFestivosConFechaIncorrecta() {
		String strFechaMinima = "FECHA_INCORRECTA";
		
		try {
			mockMvc.perform(get("/api/novedad/listar-festivos/" + strFechaMinima)
					.contentType(MediaType.APPLICATION_JSON));
			fail("Deberia fallar por: " + NovedadControlador.ERROR_LISTAR_FESTIVOS_PARSE_FECHA);
		} catch(Exception ex) {
			assertTrue(ex.getMessage().contains(NovedadControlador.ERROR_LISTAR_FESTIVOS_PARSE_FECHA));
		}
	}
	

	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdIsNull() {
		ComandoBarbero barberoMock = ComandoBarbero.builder().id(null).nombre("").build();
		
		ComandoNovedad novedadMock = ComandoNovedad.builder()
				.barbero(barberoMock)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
	
	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdIsZero() {
		ComandoBarbero barberoMock = ComandoBarbero.builder().id(0l).nombre("").build();
		
		ComandoNovedad novedadMock = ComandoNovedad.builder()
				.barbero(barberoMock)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
	
	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdNull() {		
		ComandoNovedad novedadMock = ComandoNovedad.builder()
				.barbero(null)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
}
