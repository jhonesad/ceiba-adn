package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;
import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaTestDataBuilder;

public class ManejadorCitasTest {

	private ServicioCita servicioCita;
	private FabricaCita fabricaCita;
	private ManejadorCitas manejadorCitas;
	
	@Before
	public void setUp() {
		servicioCita = Mockito.mock(ServicioCita.class);
		fabricaCita = Mockito.mock(FabricaCita.class);
		manejadorCitas = new ManejadorCitas(servicioCita, fabricaCita);
	}
	
	@Test
	public void listarCitas() {
		Cita citaMock = CitaTestDataBuilder.aCitaTestDataBuilder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		Mockito.when(servicioCita.listarCitas()).thenReturn(listaCitasMock);
		
		List<Cita> citas = manejadorCitas.listarCitas();
		
		assertFalse(citas.isEmpty());
	}
	
	@Test
	public void agendarCita() {
		Date fecha = new Date(); 
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		Boolean corteCabello = true; 
		Boolean corteBarba = false; 
		Boolean lavado = true; 
		String nombreCliente = "cliente";
		
		Cita citaMock = CitaTestDataBuilder.aCitaTestDataBuilder()
				.withFecha(fecha)
				.withBarbero(barbero)
				.withCorteCabello(corteCabello)
				.withCorteBarba(corteBarba)
				.withLavado(lavado)
				.withNombreCliente(nombreCliente)
				.build();
		Mockito.when(fabricaCita.cita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente))
			.thenReturn(citaMock);
		Mockito.when(servicioCita.agendarCita(citaMock)).thenReturn(citaMock);
		
		Cita cita = manejadorCitas.agendarCita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente);
		
		assertNotNull(cita);
		assertEquals(citaMock, cita);
	}
}
