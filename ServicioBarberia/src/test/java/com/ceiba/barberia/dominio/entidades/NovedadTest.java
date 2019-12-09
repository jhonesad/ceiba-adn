package com.ceiba.barberia.dominio.entidades;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;

public class NovedadTest {

	@Test
	public void validarInstancia() {
		Novedad novedad = new Novedad();
		
		assertNotNull(novedad);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		String descripcion = "descripcion prueba";
		Boolean festivo = true;
		
		Novedad novedad = new Novedad(null, new Date(), new Date(), null, "", false);
		novedad.setId(id);
		novedad.setFechaInicio(fechaInicio);
		novedad.setFechaFin(fechaFin);
		novedad.setBarbero(barbero);
		novedad.setDescripcion(descripcion);
		novedad.setFestivo(festivo);
		
		assertEquals(id, novedad.getId());
		assertEquals(fechaInicio, novedad.getFechaInicio());
		assertEquals(fechaFin, novedad.getFechaFin());
		assertNotNull(novedad.getBarbero());
		assertEquals(barbero.getId(), novedad.getBarbero().getId());
		assertEquals(barbero.getNombre(), novedad.getBarbero().getNombre());
		assertEquals(descripcion, novedad.getDescripcion());
		assertEquals(festivo, novedad.getFestivo());
	}
}
