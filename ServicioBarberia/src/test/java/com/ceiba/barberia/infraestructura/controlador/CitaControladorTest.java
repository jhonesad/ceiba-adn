package com.ceiba.barberia.infraestructura.controlador;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorCitas;
import com.ceiba.barberia.testdatabuilder.ComandoCitaDataBuilder;

public class CitaControladorTest extends ControladorText {
	
	CitaControlador citaControlador;
	
	@Mock
	ManejadorCitas manejadorCitas;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		citaControlador = new CitaControlador(manejadorCitas);
		mockMvc = MockMvcBuilders.standaloneSetup(citaControlador).build();
	}
	
	@Test
	public void agendarCita() throws Exception {
		Long id = 99l;
		ComandoCita citaMock = ComandoCitaDataBuilder.aComandoCitaDataBuilder().withId(id).build();
		Mockito.when(manejadorCitas.agendarCita(Mockito.any(ComandoCita.class))).thenReturn(citaMock);
		
		mockMvc.perform(post("/barberia/agendar-cita")
				.content(asJsonString(citaMock))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(id));
		
		Mockito.verify(manejadorCitas, Mockito.times(1)).agendarCita(Mockito.any(ComandoCita.class));
	}
	
	@Test
	public void listarCitas() throws Exception {
		ComandoCita citaMock = ComandoCitaDataBuilder.aComandoCitaDataBuilder().build();
		List<ComandoCita> listaCitasMock = new ArrayList<ComandoCita>();
		listaCitasMock.add(citaMock);
		Mockito.when(manejadorCitas.listarCitas()).thenReturn(listaCitasMock);
		
		mockMvc.perform(get("/barberia/listar-citas")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$", hasSize(listaCitasMock.size())));
		
		Mockito.verify(manejadorCitas, Mockito.times(1)).listarCitas();
	}
}
