package com.ceiba.barberia.dominio.servicio;

import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;
import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class ServicioBarberoTest {

	private RepositorioBarbero repositorioBarbero;
	private ServicioBarbero servicioBarbero;
	
	@Before
	public void setUp() {
		repositorioBarbero = Mockito.mock(RepositorioBarbero.class);
		servicioBarbero = new ServicioBarbero(repositorioBarbero);
	}
	
	@Test
	public void listarBarberos() {
		Barbero barbero = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		List<Barbero> listaMock = new ArrayList<Barbero>();
		listaMock.add(barbero);
		Mockito.when(repositorioBarbero.listar()).thenReturn(listaMock);
		
		List<Barbero> listaBarberos = servicioBarbero.listar();
		
		assertFalse(listaBarberos.isEmpty());
	}
	
	@Test
	public void crearBarbero() {
		Barbero barberoMock = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		Mockito.when(repositorioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		
		Barbero barbero = servicioBarbero.crear(barberoMock);
		
		assertNotNull(barbero);
		assertEquals(barberoMock.getCodigo(), barbero.getCodigo());
	}
}
