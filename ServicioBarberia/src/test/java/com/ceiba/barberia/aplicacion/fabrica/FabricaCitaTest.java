package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class FabricaCitaTest {

	@Test
	public void validarInstanciaFabrica() {
		Date fecha = new Date(); 
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build(); 
		Boolean corteCabello = true;
		Boolean corteBarba = true;
		Boolean lavado = true;
		String nombreCliente = "cliente";
		FabricaCita fabrica = new FabricaCita();
		
		Cita instancia = fabrica.cita(fecha, barbero, corteCabello, corteBarba, lavado, nombreCliente);
		
		assertNotNull(instancia);
		assertEquals(fecha, instancia.getFecha());
		assertNotNull(instancia.getBarbero());
		assertEquals(barbero.getCodigo(), instancia.getBarbero().getCodigo());
		assertEquals(barbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(corteCabello, instancia.getCorteCabello());
		assertEquals(corteBarba, instancia.getCorteBarba());
		assertEquals(lavado, instancia.getLavado());
		assertEquals(nombreCliente, instancia.getNombreCliente());
	}
}
