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

public class ManejadorListarBarberosTest {

	private ServicioBarbero servicioBarbero;
	private FabricaBarbero fabricaBarbero;
	private ManejadorListarBarberos manejadorListarBarberos;
	
	@Before
	public void setUp() {
		servicioBarbero = Mockito.mock(ServicioBarbero.class);
		fabricaBarbero = Mockito.mock(FabricaBarbero.class);
		manejadorListarBarberos = new ManejadorListarBarberos(servicioBarbero, fabricaBarbero);
	}
	
	@Test
	public void listarBarberos() {
		Barbero barberoMock = Barbero.builder().id(1l).nombre("test").build(); 
		List<Barbero> listaBarberosMock = new ArrayList<Barbero>();
		listaBarberosMock.add(barberoMock);
		Mockito.when(servicioBarbero.listar()).thenReturn(listaBarberosMock);
		
		List<ComandoBarbero> barberos = manejadorListarBarberos.ejecutar();
		
		assertFalse(barberos.isEmpty());
	}
}
