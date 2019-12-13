package com.ceiba.barberia.infraestructura.controlador;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorNovedades;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoNovedadDataBuilder;


public class NovedadControladorTest extends ControladorText {
	
	NovedadControlador novedadControlador;
	
	@Mock
	ManejadorNovedades manejadorNovedades;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		novedadControlador = Mockito.spy(new NovedadControlador(manejadorNovedades));
		mockMvc = MockMvcBuilders.standaloneSetup(novedadControlador).build();
	}
	
	@Test
	public void crearNovedad() throws Exception {
		Long id = 99l;
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder().withId(id).withBarbero(null).build();
		Mockito.when(manejadorNovedades.crear(Mockito.any(ComandoNovedad.class))).thenReturn(novedadMock);
		Mockito.doNothing().when(novedadControlador).validateAndSetBarberoNuevaNovedad(Mockito.any(ComandoNovedad.class));
		
		mockMvc.perform(post("/barberia/crear-novedad")
				.content(asJsonString(novedadMock))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(id));
		
		Mockito.verify(manejadorNovedades, Mockito.times(1)).crear(Mockito.any(ComandoNovedad.class));
	}
	
	@Test
	public void listarNovedades() throws Exception {
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder().build();
		List<ComandoNovedad> listaNovedadesMock = new ArrayList<ComandoNovedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(manejadorNovedades.listar()).thenReturn(listaNovedadesMock);
		
		mockMvc.perform(get("/barberia/listar-novedades")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(listaNovedadesMock.size())));
		
		Mockito.verify(manejadorNovedades, Mockito.times(1)).listar();
	}
	
	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdIsNull() {
		ComandoBarbero barberoMock = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder()
				.withId(null)
				.withNombre("")
				.build();
		
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withBarbero(barberoMock)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
	
	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdIsZero() {
		ComandoBarbero barberoMock = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder()
				.withId(0l)
				.withNombre("")
				.build();
		
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withBarbero(barberoMock)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
	
	@Test
	public void setBarberoNuevaNovedadWhenBarberoIdNull() {		
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withBarbero(null)
				.build();
		
		novedadControlador.validateAndSetBarberoNuevaNovedad(novedadMock);
		
		assertNull(novedadMock.getBarbero());
	}
	
	@Test
	public void listarFestivos() throws Exception {
		String strFechaMinima = "13-12-2019";
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withBarbero(null)
				.withFestivo(true)
				.build();
		List<ComandoNovedad> listaNovedadesMock = new ArrayList<ComandoNovedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(manejadorNovedades.listarFestivos(Mockito.any(Date.class))).thenReturn(listaNovedadesMock);
		
		mockMvc.perform(get("/barberia/listar-festivos/" + strFechaMinima)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(listaNovedadesMock.size())));
		
		Mockito.verify(manejadorNovedades, Mockito.times(1)).listarFestivos(Mockito.any(Date.class));
	}
	
	@Test
	public void listarFestivosConFechaIncorrecta() {
		String strFechaMinima = "FECHA_INCORRECTA";
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withBarbero(null)
				.withFestivo(true)
				.build();
		List<ComandoNovedad> listaNovedadesMock = new ArrayList<ComandoNovedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(manejadorNovedades.listarFestivos(Mockito.any(Date.class))).thenReturn(listaNovedadesMock);
		
		try {
			mockMvc.perform(get("/barberia/listar-festivos/" + strFechaMinima)
					.contentType(MediaType.APPLICATION_JSON));
			fail("Deberia fallar por: " + NovedadControlador.ERROR_LISTAR_FESTIVOS_PARSE_FECHA);
		} catch(Exception ex) {
			assertTrue(ex.getMessage().contains(NovedadControlador.ERROR_LISTAR_FESTIVOS_PARSE_FECHA));
		}
	}
}
