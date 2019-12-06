package com.ceiba.barberia.dominio.servicio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.testdatabuilder.CitaTestDataBuilder;

public class ServicioCitaTest {
	
	private RepositorioCita repositorioCita;
	private ServicioCita servicioCita;
	
	@Before
	public void setUp() {
		repositorioCita = Mockito.mock(RepositorioCita.class);
		servicioCita = new ServicioCita(repositorioCita);
	}
	
	@Test
	public void listarCitas() {
		Cita citaMock = CitaTestDataBuilder.aCitaTestDataBuilder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		Mockito.when(repositorioCita.retornar()).thenReturn(listaCitasMock);
		
		List<Cita> listaCitas = servicioCita.listarCitas();
		
		assertFalse(listaCitas.isEmpty());
	}
	
	@Test
	public void agendarCita() {
		Cita citaMock = CitaTestDataBuilder.aCitaTestDataBuilder().build();
		Mockito.when(repositorioCita.crear(citaMock)).thenReturn(citaMock);
		
		Cita cita = servicioCita.agendarCita(citaMock);
		
		assertNotNull(cita);
		assertEquals(citaMock, cita);
	}
}
