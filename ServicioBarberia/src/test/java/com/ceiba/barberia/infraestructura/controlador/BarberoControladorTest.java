package com.ceiba.barberia.infraestructura.controlador;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorBarberos;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

public class BarberoControladorTest extends ControladorText {
	
	BarberoControlador barberoControlador;
	
	@Mock
	ManejadorBarberos manejadorBarberos;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		barberoControlador = new BarberoControlador(manejadorBarberos);
		mockMvc = MockMvcBuilders.standaloneSetup(barberoControlador).build();
	}
	
	@Test
	public void crearBarbero() throws Exception {
		Long id = 99l;
		ComandoBarbero barberoMock = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().withId(id).build();
		Mockito.when(manejadorBarberos.crear(Mockito.any(ComandoBarbero.class))).thenReturn(barberoMock);
		
		mockMvc.perform(post("/barberia/crear-barbero")
				.content(asJsonString(barberoMock))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(id));
		
		Mockito.verify(manejadorBarberos, Mockito.times(1)).crear(Mockito.any(ComandoBarbero.class));
	}
	
	@Test
	public void listarBarberos() throws Exception {
		ComandoBarbero barberoMock = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		List<ComandoBarbero> listaBarberosMock = new ArrayList<ComandoBarbero>();
		listaBarberosMock.add(barberoMock);
		Mockito.when(manejadorBarberos.listar()).thenReturn(listaBarberosMock);
		
		mockMvc.perform(get("/barberia/listar-barberos")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(listaBarberosMock.size())));
		
		Mockito.verify(manejadorBarberos, Mockito.times(1)).listar();
	}
}
