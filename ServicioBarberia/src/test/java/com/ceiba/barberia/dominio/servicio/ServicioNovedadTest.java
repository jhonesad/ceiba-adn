package com.ceiba.barberia.dominio.servicio;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;

public class ServicioNovedadTest {

	private RepositorioNovedad repositorioNovedad;
	private ServicioNovedad servicioNovedad;
	
	@Before
	public void setUp() {
		repositorioNovedad = Mockito.mock(RepositorioNovedad.class);
		servicioNovedad = new ServicioNovedad(repositorioNovedad);
	}
	
	@Test
	public void listarNovedades() {
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		List<Novedad> listaNovedadesMock = new ArrayList<Novedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(repositorioNovedad.retornar()).thenReturn(listaNovedadesMock);
		
		List<Novedad> listaNovedades = servicioNovedad.listar();
		
		assertFalse(listaNovedades.isEmpty());
	}
	
	@Test
	public void listarFestivos() {
		Date fechaMinima = new Date();
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		List<Novedad> listaNovedadesMock = new ArrayList<Novedad>();
		listaNovedadesMock.add(novedadMock);
		Mockito.when(repositorioNovedad.listarFestivos(fechaMinima)).thenReturn(listaNovedadesMock);
		
		List<Novedad> listaNovedades = servicioNovedad.listarFestivos(fechaMinima);
		
		assertFalse(listaNovedades.isEmpty());
	}
	
	@Test
	public void crearNovedad() {
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		Mockito.when(repositorioNovedad.crear(novedadMock)).thenReturn(novedadMock);
		
		Novedad novedad = servicioNovedad.crear(novedadMock);
		
		assertNotNull(novedad);
		assertEquals(novedadMock.getId(), novedad.getId());
	}
}
