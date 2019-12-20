package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

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
		ComandoBarbero comandoBarbero = ComandoBarbero.builder().id(1l).nombre("test").build(); 
		Boolean corteCabello = true;
		Boolean corteBarba = true;
		Boolean lavado = true;
		String nombreCliente = "cliente";
		
		ComandoCita comandoCita = ComandoCita.builder()
				.id(id)
				.fecha(fecha)
				.barbero(comandoBarbero)
				.corteCabello(corteCabello)
				.corteBarba(corteBarba)
				.lavado(lavado)
				.nombreCliente(nombreCliente)
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
		Barbero barbero = Barbero.builder().id(1l).nombre("test").build(); 
		Boolean corteCabello = true;
		Boolean corteBarba = true;
		Boolean lavado = true;
		String nombreCliente = "cliente";
		
		Cita cita = Cita.builder()
				.id(id)
				.fecha(fecha)
				.barbero(barbero)
				.corteCabello(corteCabello)
				.corteBarba(corteBarba)
				.lavado(lavado)
				.nombreCliente(nombreCliente)
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
