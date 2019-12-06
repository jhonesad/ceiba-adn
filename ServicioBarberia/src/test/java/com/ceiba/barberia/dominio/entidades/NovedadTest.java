package com.ceiba.barberia.dominio.entidades;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class NovedadTest {

	@Test
	public void validarInstancia() {
		Novedad novedad = new Novedad();
		
		assertNotNull(novedad);
	}
	
	@Test
	public void validarGettersSetters() {
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		String descripcion = "descripcion prueba";
		Boolean festivo = true;
		
		Novedad novedad = new Novedad();
		novedad.setFechaInicio(fechaInicio);
		novedad.setFechaFin(fechaFin);
		novedad.setBarbero(barbero);
		novedad.setDescripcion(descripcion);
		novedad.setFestivo(festivo);
		
		assertEquals(fechaInicio, novedad.getFechaInicio());
		assertEquals(fechaFin, novedad.getFechaFin());
		assertNotNull(novedad.getBarbero());
		assertEquals(barbero.getCodigo(), novedad.getBarbero().getCodigo());
		assertEquals(barbero.getNombre(), novedad.getBarbero().getNombre());
		assertEquals(descripcion, novedad.getDescripcion());
		assertEquals(festivo, novedad.getFestivo());
	}
}
