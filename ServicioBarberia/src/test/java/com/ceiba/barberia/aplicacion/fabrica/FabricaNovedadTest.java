package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class FabricaNovedadTest {

	@Test
	public void validarInstanciaFabrica() {
		Date fechaInicio = new Date(); 
		Date fechaFin = new Date();
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		String descripcion = "descripcion";
		Boolean festivo = false;
		FabricaNovedad fabrica = new FabricaNovedad();
		
		Novedad instancia = fabrica.novedad(fechaInicio, fechaFin, barbero, descripcion, festivo);
		
		assertNotNull(instancia);
		assertEquals(fechaInicio, instancia.getFechaInicio());
		assertEquals(fechaFin, instancia.getFechaFin());
		assertNotNull(instancia.getBarbero());
		assertEquals(barbero.getCodigo(), instancia.getBarbero().getCodigo());
		assertEquals(barbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(descripcion, instancia.getDescripcion());
		assertEquals(festivo, instancia.getFestivo());
	}
}
