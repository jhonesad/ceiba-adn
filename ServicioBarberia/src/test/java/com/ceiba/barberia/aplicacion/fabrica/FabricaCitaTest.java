package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoCitaDataBuilder;

public class FabricaCitaTest {
	
	private FabricaCita fabrica;
	
	@Before
	public void setUp() {
		fabrica = new FabricaCita();
	}

	@Test
	public void validarCrearCitaDominio() {
		Long id = 3l;
		Date fecha = new Date(); 
		ComandoBarbero comandoBarbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build(); 
		Boolean corteCabello = true;
		Boolean corteBarba = true;
		Boolean lavado = true;
		String nombreCliente = "cliente";
		
		ComandoCita comandoCita = ComandoCitaDataBuilder.aComandoCitaDataBuilder()
				.withId(id)
				.withFecha(fecha)
				.withBarbero(comandoBarbero)
				.withCorteCabello(corteCabello)
				.withCorteBarba(corteBarba)
				.withLavado(lavado)
				.withNombreCliente(nombreCliente)
				.build();
		
		Cita instancia = fabrica.crear(comandoCita);
		
		assertNotNull(instancia);
		assertEquals(fecha, instancia.getFecha());
		assertNotNull(instancia.getBarbero());
		assertEquals(id, instancia.getId());
		assertEquals(comandoBarbero.getId(), instancia.getBarbero().getId());
		assertEquals(comandoBarbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(corteCabello, instancia.getCorteCabello());
		assertEquals(corteBarba, instancia.getCorteBarba());
		assertEquals(lavado, instancia.getLavado());
		assertEquals(nombreCliente, instancia.getNombreCliente());
	}
	
	@Test
	public void validarCrearComandoCita() {
		Long id = 3l;
		Date fecha = new Date(); 
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().build(); 
		Boolean corteCabello = true;
		Boolean corteBarba = true;
		Boolean lavado = true;
		String nombreCliente = "cliente";
		
		Cita cita = CitaDataBuilder.aCitaDataBuilder()
				.withId(id)
				.withFecha(fecha)
				.withBarbero(barbero)
				.withCorteCabello(corteCabello)
				.withCorteBarba(corteBarba)
				.withLavado(lavado)
				.withNombreCliente(nombreCliente)
				.build();
		
		ComandoCita instancia = fabrica.cita(cita);
		
		assertNotNull(instancia);
		assertEquals(fecha, instancia.getFecha());
		assertNotNull(instancia.getBarbero());
		assertEquals(id, instancia.getId());
		assertEquals(barbero.getId(), instancia.getBarbero().getId());
		assertEquals(barbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(corteCabello, instancia.getCorteCabello());
		assertEquals(corteBarba, instancia.getCorteBarba());
		assertEquals(lavado, instancia.getLavado());
		assertEquals(nombreCliente, instancia.getNombreCliente());
	}
}
