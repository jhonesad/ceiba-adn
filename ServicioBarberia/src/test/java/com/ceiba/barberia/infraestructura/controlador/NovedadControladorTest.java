package com.ceiba.barberia.infraestructura.controlador;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorNovedades;
import com.ceiba.barberia.testdatabuilder.ComandoNovedadDataBuilder;


public class NovedadControladorTest extends ControladorText {
	
	NovedadControlador novedadControlador;
	
	@Mock
	ManejadorNovedades manejadorNovedades;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		novedadControlador = new NovedadControlador(manejadorNovedades);
		mockMvc = MockMvcBuilders.standaloneSetup(novedadControlador).build();
	}
	
	@Test
	public void crearNovedad() throws Exception {
		Long id = 99l;
		ComandoNovedad novedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder().withId(id).build();
		Mockito.when(manejadorNovedades.crear(Mockito.any(ComandoNovedad.class))).thenReturn(novedadMock);
		
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
}
