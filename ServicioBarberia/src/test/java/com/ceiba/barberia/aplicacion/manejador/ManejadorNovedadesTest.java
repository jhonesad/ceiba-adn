package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;
import com.ceiba.barberia.testdatabuilder.ComandoNovedadDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;

public class ManejadorNovedadesTest {

	private ServicioNovedad servicioNovedad;
	private FabricaNovedad fabricaNovedad;
	private ManejadorNovedades manejadorNovedades;
	
	@Before
	public void setUp() {
		servicioNovedad = Mockito.mock(ServicioNovedad.class);
		fabricaNovedad = Mockito.mock(FabricaNovedad.class);
		manejadorNovedades = new ManejadorNovedades(servicioNovedad, fabricaNovedad);
	}
	
	@Test
	public void listarNovedades() {
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		ComandoNovedad comandoNovedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder().build();
		List<Novedad> listaNovedadesMock = new ArrayList<Novedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(servicioNovedad.listar()).thenReturn(listaNovedadesMock);
		Mockito.when(fabricaNovedad.novedad(novedadMock)).thenReturn(comandoNovedadMock);
		
		List<ComandoNovedad> novedades = manejadorNovedades.listar();
		
		assertFalse(novedades.isEmpty());
		assertEquals(comandoNovedadMock, novedades.get(0));
	}
	
	@Test
	public void crearNovedad() {
		Long id = 2l;
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().withId(id).build();
		ComandoNovedad comandoNovedadMock = ComandoNovedadDataBuilder.aComandoNovedadDataBuilder().withId(null).build();
		Mockito.when(fabricaNovedad.crear(comandoNovedadMock)).thenReturn(novedadMock);
		Mockito.when(servicioNovedad.crear(novedadMock)).thenReturn(novedadMock);
		
		ComandoNovedad novedad = manejadorNovedades.crear(comandoNovedadMock);
		
		assertNotNull(novedad);
		assertEquals(id, novedad.getId());
	}
}
