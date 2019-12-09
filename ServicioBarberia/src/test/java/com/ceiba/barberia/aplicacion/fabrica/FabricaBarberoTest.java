package com.ceiba.barberia.aplicacion.fabrica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;

public class FabricaBarberoTest {
	
	private FabricaBarbero fabrica;
	
	@Before
	public void setUp() {
		fabrica = new FabricaBarbero();
	}
	

	@Test
	public void validarCrearBarberoDominio() {
		Long id = 9l;
		String nombre = "nombre";
		
		ComandoBarbero comandoBarbero = ComandoBarberoDataBuilder
				.aComandoBarberoDataBuilder()
				.withId(id)
				.withNombre(nombre)
				.build();
		
		Barbero instancia = fabrica.crear(comandoBarbero);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(nombre, instancia.getNombre());
	}
	
	@Test
	public void validarCrearComandoBarbero() {
		Long id = 9l;
		String nombre = "nombre";
		
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder()
				.withId(id)
				.withNombre(nombre)
				.build();
		
		ComandoBarbero instancia = fabrica.barbero(barbero);
		
		assertNotNull(instancia);
		assertEquals(id, instancia.getId());
		assertEquals(nombre, instancia.getNombre());
	}
	
	@Test
	public void getInstance() {
		FabricaBarbero instancia = FabricaBarbero.getInstance();
		
		assertNotNull(instancia);
	}
}
