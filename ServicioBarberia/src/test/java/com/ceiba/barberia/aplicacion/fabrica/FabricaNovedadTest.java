package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoNovedadDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;

public class FabricaNovedadTest {
	
	private FabricaNovedad fabrica;
	
	@Before
	public void setUp() {
		fabrica = new FabricaNovedad();
	}

	@Test
	public void validarCrearNovedadDominio() {
		Long id = 1L;
		Date fechaInicio = new Date(); 
		Date fechaFin = new Date();
		ComandoBarbero comandoBarbero = ComandoBarberoDataBuilder.aComandoBarberoDataBuilder().build();
		String descripcion = "descripcion";
		Boolean festivo = false;
		
		ComandoNovedad comandoNovedad = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withId(id)
				.withFechaInicio(fechaInicio)
				.withFechaFin(fechaFin)
				.withBarbero(comandoBarbero)
				.withDescripcion(descripcion)
				.withFestivo(festivo)
				.build();
		
		Novedad instancia = fabrica.crear(comandoNovedad);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(fechaInicio, instancia.getFechaInicio());
		assertEquals(fechaFin, instancia.getFechaFin());
		assertNotNull(instancia.getBarbero());
		assertEquals(comandoBarbero.getId(), instancia.getBarbero().getId());
		assertEquals(comandoBarbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(descripcion, instancia.getDescripcion());
		assertEquals(festivo, instancia.getFestivo());
	}
	
	@Test
	public void validarCrearNovedadDominioSinBarbero() {
		Long id = 1L;
		Date fechaInicio = new Date(); 
		Date fechaFin = new Date();
		String descripcion = "descripcion";
		Boolean festivo = false;
		
		ComandoNovedad comandoNovedad = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder()
				.withId(id)
				.withFechaInicio(fechaInicio)
				.withFechaFin(fechaFin)
				.withBarbero(null)
				.withDescripcion(descripcion)
				.withFestivo(festivo)
				.build();
		
		Novedad instancia = fabrica.crear(comandoNovedad);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(fechaInicio, instancia.getFechaInicio());
		assertEquals(fechaFin, instancia.getFechaFin());
		assertNull(instancia.getBarbero());
		assertEquals(descripcion, instancia.getDescripcion());
		assertEquals(festivo, instancia.getFestivo());
	}
	
	@Test
	public void validarCrearComandoNovedad() {
		Long id = 1L;
		Date fechaInicio = new Date(); 
		Date fechaFin = new Date();
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		String descripcion = "descripcion";
		Boolean festivo = false;
		
		Novedad novedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withId(id)
				.withFechaInicio(fechaInicio)
				.withFechaFin(fechaFin)
				.withBarbero(barbero)
				.withDescripcion(descripcion)
				.withFestivo(festivo)
				.build();
		
		ComandoNovedad instancia = fabrica.novedad(novedad);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(fechaInicio, instancia.getFechaInicio());
		assertEquals(fechaFin, instancia.getFechaFin());
		assertNotNull(instancia.getBarbero());
		assertEquals(barbero.getId(), instancia.getBarbero().getId());
		assertEquals(barbero.getNombre(), instancia.getBarbero().getNombre());
		assertEquals(descripcion, instancia.getDescripcion());
		assertEquals(festivo, instancia.getFestivo());
	}
	
	@Test
	public void validarCrearComandoNovedadSinBarbero() {
		Long id = 1L;
		Date fechaInicio = new Date(); 
		Date fechaFin = new Date();
		String descripcion = "descripcion";
		Boolean festivo = false;
		
		Novedad novedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withId(id)
				.withFechaInicio(fechaInicio)
				.withFechaFin(fechaFin)
				.withBarbero(null)
				.withDescripcion(descripcion)
				.withFestivo(festivo)
				.build();
		
		ComandoNovedad instancia = fabrica.novedad(novedad);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(fechaInicio, instancia.getFechaInicio());
		assertEquals(fechaFin, instancia.getFechaFin());
		assertNull(instancia.getBarbero());
		assertEquals(descripcion, instancia.getDescripcion());
		assertEquals(festivo, instancia.getFestivo());
	}
}
