package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

public class ManejadorListarFestivosTest {

	private ServicioNovedad servicioNovedad;
	private FabricaNovedad fabricaNovedad;
	private ManejadorListarFestivos manejadorListarFestivos;
	
	@Before
	public void setUp() {
		servicioNovedad = Mockito.mock(ServicioNovedad.class);
		fabricaNovedad = new FabricaNovedad();
		manejadorListarFestivos = new ManejadorListarFestivos(servicioNovedad, fabricaNovedad);
	}
	
	@Test
	public void listarFestivos() {
		Date fechaMinima = new Date();
		Novedad novedadMock = Novedad.builder().build();
		ComandoNovedad comandoNovedadMock = ComandoNovedad.builder().build();
		List<Novedad> listaNovedadesMock = new ArrayList<>();
		listaNovedadesMock.add(novedadMock);
		List<ComandoNovedad> listaComandoNovedadesMock = new ArrayList<>();
		listaComandoNovedadesMock.add(comandoNovedadMock);
		Mockito.when(servicioNovedad.listarFestivos(fechaMinima)).thenReturn(listaNovedadesMock);
		
		List<ComandoNovedad> novedades = manejadorListarFestivos.ejecutar(fechaMinima);
		
		assertFalse(novedades.isEmpty());
		assertEquals(listaNovedadesMock.size(), novedades.size());
	}
}
