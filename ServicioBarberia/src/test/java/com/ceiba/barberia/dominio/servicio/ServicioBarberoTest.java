package com.ceiba.barberia.dominio.servicio;

import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.exception.BarberiaBusinessLogicException;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;

public class ServicioBarberoTest {

	private RepositorioBarbero repositorioBarbero;
	private ServicioBarbero servicioBarbero;
	
	@Before
	public void setUp() {
		repositorioBarbero = Mockito.mock(RepositorioBarbero.class);
		servicioBarbero = Mockito.spy(new ServicioBarbero(repositorioBarbero));
	}
	
	@Test
	public void listarBarberos() {
		Barbero barbero = BarberoDataBuilder.aBarberoDataBuilder().build();
		List<Barbero> listaMock = new ArrayList<>();
		listaMock.add(barbero);
		Mockito.when(repositorioBarbero.listar()).thenReturn(listaMock);
		
		List<Barbero> listaBarberos = servicioBarbero.listar();
		
		assertFalse(listaBarberos.isEmpty());
	}
	
	@Test
	public void crearBarbero() {
		Barbero barberoMock = BarberoDataBuilder.aBarberoDataBuilder().build();
		Mockito.when(repositorioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		Mockito.doReturn(false).when(servicioBarbero).nombreBarberoExisteEnBD(barberoMock.getNombre());
		
		Barbero barbero = servicioBarbero.crear(barberoMock);
		
		assertNotNull(barbero);
		assertEquals(barberoMock.getId(), barbero.getId());
	}
	
	@Test
	public void crearBarberoQueExisteEnBD() {
		Barbero barberoMock = BarberoDataBuilder.aBarberoDataBuilder().build();
		Mockito.when(repositorioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		Mockito.doReturn(true).when(servicioBarbero).nombreBarberoExisteEnBD(barberoMock.getNombre());
		
		try {
			servicioBarbero.crear(barberoMock);
			fail("Deberia retornar excepcion por: " + ServicioBarbero.ERROR_BARBERO_GUARDADO);
		} catch(Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioBarbero.ERROR_BARBERO_GUARDADO));
		}
	}
	
	@Test
	public void nombreBarberoExisteEnBD() {
		String nombreConsulta = "bar";
		Barbero barbero1 = BarberoDataBuilder.aBarberoDataBuilder().withId(1l).withNombre(nombreConsulta).build();
		List<Barbero> listaMock = new ArrayList<>();
		listaMock.add(barbero1);
		Mockito.when(repositorioBarbero.listarPorNombre(nombreConsulta)).thenReturn(listaMock);
		
		boolean validacion = servicioBarbero.nombreBarberoExisteEnBD(nombreConsulta);
		
		assertTrue(validacion);
	}
	
	@Test
	public void nombreBarberoNoExisteEnBD() {
		String nombreConsulta = "bar";
		Mockito.when(repositorioBarbero.listarPorNombre(nombreConsulta)).thenReturn(null);
		
		boolean validacion = servicioBarbero.nombreBarberoExisteEnBD(nombreConsulta);
		
		assertFalse(validacion);
	}
}
