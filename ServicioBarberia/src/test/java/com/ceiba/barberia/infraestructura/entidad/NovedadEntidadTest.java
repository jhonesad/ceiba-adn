package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

public class NovedadEntidadTest {

	@Test
	public void validarGettersSetters() {
		Long id = 1l;
		Date fechaInicio = new Date();
		Date fechaFin = new Date();
		BarberoEntidad barbero = new BarberoEntidad();
		String descripcion = "festivo";
		Boolean festivo = true;
		
		NovedadEntidad novedad = new NovedadEntidad();
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
