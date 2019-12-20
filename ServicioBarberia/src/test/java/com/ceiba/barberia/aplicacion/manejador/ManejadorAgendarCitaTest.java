package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

public class ManejadorAgendarCitaTest {

	private ServicioCita servicioCita;
	private FabricaCita fabricaCita;
	private ManejadorAgendarCita manejadorAgendarCita;
	
	@Before
	public void setUp() {
		servicioCita = Mockito.mock(ServicioCita.class);
		fabricaCita = Mockito.mock(FabricaCita.class);
		manejadorAgendarCita = new ManejadorAgendarCita(servicioCita, fabricaCita);
	}
	
	@Test
	public void agendarCita() {
		Long id = 5l;
		Date fecha = new Date(); 
		Barbero barbero = Barbero.builder().id(1l).nombre("test").build(); 
		Boolean corteCabello = true; 
		Boolean corteBarba = false; 
		Boolean lavado = true; 
		String nombreCliente = "cliente";
		
		Cita citaMock = Cita.builder()
				.id(id)
				.fecha(fecha)
				.barbero(barbero)
				.corteCabello(corteCabello)
				.corteBarba(corteBarba)
				.lavado(lavado)
				.nombreCliente(nombreCliente)
				.build();
		ComandoCita comandoCitaMock = ComandoCita
				.builder()
				.id(null)
				.fecha(fecha)
				.barbero(ComandoBarbero.builder()
						.id(barbero.getId())
						.nombre(barbero.getNombre())
						.build())
				.corteCabello(corteCabello)
				.corteBarba(corteBarba)
				.lavado(lavado)
				.nombreCliente(nombreCliente)
				.build();
		
		Mockito.when(fabricaCita.crear(comandoCitaMock))
			.thenReturn(citaMock);
		Mockito.when(servicioCita.agendar(citaMock)).thenReturn(citaMock);
		
		ComandoCita cita = manejadorAgendarCita.ejecutar(comandoCitaMock);
		
		assertNotNull(cita);
		assertEquals(id, cita.getId());
	}
}
