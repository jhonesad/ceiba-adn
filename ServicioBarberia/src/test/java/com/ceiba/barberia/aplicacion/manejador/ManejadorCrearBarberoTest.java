package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;

public class ManejadorCrearBarberoTest {

	private ServicioBarbero servicioBarbero;
	private FabricaBarbero fabricaBarbero;
	private ManejadorCrearBarbero manejadorCrearBarbero;
	
	@Before
	public void setUp() {
		servicioBarbero = Mockito.mock(ServicioBarbero.class);
		fabricaBarbero = Mockito.mock(FabricaBarbero.class);
		manejadorCrearBarbero = new ManejadorCrearBarbero(servicioBarbero, fabricaBarbero);
	}
	
	@Test
	public void crearBarbero() {
		Long id = 4l; 
		String nombre = "nombre";
		Barbero barberoMock = Barbero.builder()
				.id(id)
				.nombre(nombre)
				.build();
		ComandoBarbero comandoBarberoMock = ComandoBarbero.builder()
				.id(null)
				.nombre(nombre)
				.build();
		
		Mockito.when(fabricaBarbero.crear(comandoBarberoMock))
			.thenReturn(barberoMock);
		Mockito.when(servicioBarbero.crear(barberoMock)).thenReturn(barberoMock);
		
		ComandoBarbero barbero = manejadorCrearBarbero.ejecutar(comandoBarberoMock);
		
		assertNotNull(barbero);
		assertEquals(id, barbero.getId());
		assertEquals(nombre, barbero.getNombre());
	}
}
