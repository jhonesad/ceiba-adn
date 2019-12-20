package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoCitaDataBuilder;

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
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		Mockito.when(servicioCita.listarCitas()).thenReturn(listaCitasMock);
		
		List<ComandoCita> citas = manejadorCitas.listarCitas();
		
		assertFalse(citas.isEmpty());
	}
	
	@Test
	public void agendarCita() {
		Long id = 5l;
		Date fecha = new Date(); 
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		Boolean corteCabello = true; 
		Boolean corteBarba = false; 
		Boolean lavado = true; 
		String nombreCliente = "cliente";
		
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder()
				.withId(id)
				.withFecha(fecha)
				.withBarbero(barbero)
				.withCorteCabello(corteCabello)
				.withCorteBarba(corteBarba)
				.withLavado(lavado)
				.withNombreCliente(nombreCliente)
				.build();
		ComandoCita comandoCitaMock = ComandoCitaDataBuilder
				.aComandoCitaDataBuilder()
				.withId(null)
				.withFecha(fecha)
				.withBarbero(ComandoBarberoDataBuilder.aComandoBarberoDataBuilder()
						.withId(barbero.getId())
						.withNombre(barbero.getNombre())
						.build())
				.withCorteCabello(corteCabello)
				.withCorteBarba(corteBarba)
				.withLavado(lavado)
				.withNombreCliente(nombreCliente)
				.build();
		
		Mockito.when(fabricaCita.crear(comandoCitaMock))
			.thenReturn(citaMock);
		Mockito.when(servicioCita.agendarCita(citaMock)).thenReturn(citaMock);
		
		ComandoCita cita = manejadorCitas.agendarCita(comandoCitaMock);
		
		assertNotNull(cita);
		assertEquals(id, cita.getId());
	}
}
