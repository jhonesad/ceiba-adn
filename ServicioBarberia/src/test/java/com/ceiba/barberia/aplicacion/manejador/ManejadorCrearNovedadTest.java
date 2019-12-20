package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

public class ManejadorCrearNovedadTest {

	private ServicioNovedad servicioNovedad;
	private FabricaNovedad fabricaNovedad;
	private ManejadorCrearNovedad manejadorCrearNovedad;
	
	@Before
	public void setUp() {
		servicioNovedad = Mockito.mock(ServicioNovedad.class);
		fabricaNovedad = Mockito.mock(FabricaNovedad.class);
		manejadorCrearNovedad = new ManejadorCrearNovedad(servicioNovedad, fabricaNovedad);
	}
	
	@Test
	public void crearNovedad() {
		Long id = 2l;
		Novedad novedadMock = Novedad.builder().id(id).build();
		ComandoNovedad comandoNovedadMock = ComandoNovedad.builder().id(null).build();
		Mockito.when(fabricaNovedad.crear(comandoNovedadMock)).thenReturn(novedadMock);
		Mockito.when(servicioNovedad.crear(novedadMock)).thenReturn(novedadMock);
		
		ComandoNovedad novedad = manejadorCrearNovedad.ejecutar(comandoNovedadMock);
		
		assertNotNull(novedad);
		assertEquals(id, novedad.getId());
	}
}
