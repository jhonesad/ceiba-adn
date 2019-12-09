package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.ComandoBarberoDataBuilder;

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
		Barbero barberoMock = BarberoDataBuilder.aBarberoDataBuilder().build();
		List<Barbero> listaBarberosMock = new ArrayList<Barbero>();
		listaBarberosMock.add(barberoMock);
		Mockito.when(servicioBarbero.listar()).thenReturn(listaBarberosMock);
		
		List<ComandoBarbero> barberos = manejadorBarberos.listar();
		
		assertFalse(barberos.isEmpty());
	}
	
	@Test
	public void crearBarbero() {
		Long id = 4l; 
		String nombre = "nombre";
		Barbero barberoMock = BarberoDataBuilder.aBarberoDataBuilder()
				.withId(id)
				.withNombre(nombre)
				.build();
		ComandoBarbero comandoBarberoMock = ComandoBarberoDataBuilder
				.aComandoBarberoDataBuilder()
				.withId(null)
				.withNombre(nombre)
				.build();
		
		Mockito.when(fabricaBarbero.crear(comandoBarberoMock))
			.thenReturn(barberoMock);
		Mockito.when(servicioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		
		ComandoBarbero barbero = manejadorBarberos.crear(comandoBarberoMock);
		
		assertNotNull(barbero);
		assertEquals(id, barbero.getId());
		assertEquals(nombre, barbero.getNombre());
	}
}
