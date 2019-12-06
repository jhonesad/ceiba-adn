package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;
import com.ceiba.barberia.testdatabuilder.BarberoTestDataBuilder;

public class ManejadorBarberosTest {

	private ServicioBarbero servicioBarbero;
	private FabricaBarbero fabricaBarbero;
	private ManejadorBarberos manejadorBarberos;
	
	@Before
	public void setUp() {
		servicioBarbero = Mockito.mock(ServicioBarbero.class);
		fabricaBarbero = Mockito.mock(FabricaBarbero.class);
		manejadorBarberos = new ManejadorBarberos(servicioBarbero, fabricaBarbero);
	}
	
	@Test
	public void listarBarberos() {
		Barbero barberoMock = BarberoTestDataBuilder.aBarberoTestDataBuilder().build();
		List<Barbero> listaBarberosMock = new ArrayList<Barbero>();
		listaBarberosMock.add(barberoMock);
		Mockito.when(servicioBarbero.listar()).thenReturn(listaBarberosMock);
		
		List<Barbero> barberos = manejadorBarberos.listar();
		
		assertFalse(barberos.isEmpty());
	}
	
	@Test
	public void crearBarbero() {
		String codigo = "test1"; 
		String nombre = "nombre";
		Barbero barberoMock = new Barbero(codigo, nombre);
		Mockito.when(fabricaBarbero.barbero(codigo, nombre)).thenReturn(barberoMock);
		Mockito.when(servicioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		
		Barbero barbero = manejadorBarberos.crear(codigo, nombre);
		
		assertNotNull(barbero);
		assertEquals(codigo, barbero.getCodigo());
		assertEquals(nombre, barbero.getNombre());
	}
}
