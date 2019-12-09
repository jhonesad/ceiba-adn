package com.ceiba.barberia.aplicacion.comando;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;

public class ComandoNovedadTest {

	@Test
	public void validarInstancia() {
		ComandoNovedad cita = new ComandoNovedad();
		
		assertNotNull(cita);
	}
	
	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		ComandoBarbero barbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		String descripcion = "descripcion prueba";
		Boolean festivo = true;
		
		ComandoNovedad novedad = new ComandoNovedad(null, new Date(), new Date(), null, "", false);
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
