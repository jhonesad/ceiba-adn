package com.ceiba.barberia.dominio.servicio;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;
import com.ceiba.barberia.testdatabuilder.CitaDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;

public class ServicioCitaTest {
	
	private RepositorioCita repositorioCita;
	private RepositorioNovedad repositorioNovedad;
	private ServicioCita servicioCita;
	private SimpleDateFormat formatter;
	
	@Before
	public void setUp() {
		repositorioCita = Mockito.mock(RepositorioCita.class);
		repositorioNovedad = Mockito.mock(RepositorioNovedad.class);
		servicioCita = Mockito.spy(new ServicioCita(repositorioCita, repositorioNovedad));
		formatter = new SimpleDateFormat("yyyyMMdd HH:mm");
	}
	
	@Test
	public void listarCitas() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		Mockito.when(repositorioCita.retornar()).thenReturn(listaCitasMock);
		
		List<Cita> listaCitas = servicioCita.listarCitas();
		
		assertFalse(listaCitas.isEmpty());
	}
	
	@Test
	public void agendarCitaSinNovedadesYFechaDisponible() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.when(repositorioCita.crear(citaMock)).thenReturn(citaMock);
		Mockito.doReturn(false).when(servicioCita).barberoTieneNovedadEnFechaCita(citaMock);
		Mockito.doReturn(false).when(servicioCita).barberoYaTieneCitaAsignadaEnFechaCita(citaMock);
		Mockito.doReturn(false).when(servicioCita).esFechaCitaMenorAlMomento(citaMock);
		
		Cita cita = servicioCita.agendarCita(citaMock);
		
		assertNotNull(cita);
		assertEquals(citaMock, cita);
	}
	
	@Test
	public void agendarCitaConNovedadEnFecha() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.doReturn(true).when(servicioCita).barberoTieneNovedadEnFechaCita(citaMock);
		
		try {
			servicioCita.agendarCita(citaMock);
			fail("Deberia retornar excepcion por: " + ServicioCita.ERROR_BARBERO_CON_NOVEDAD_EN_FECHA);
		} catch(Exception ex) {
			assertTrue(ex instanceof RuntimeException);
			assertTrue(ex.getMessage().contains(ServicioCita.ERROR_BARBERO_CON_NOVEDAD_EN_FECHA));
		}
	}
	
	@Test
	public void agendarCitaSinDisponibilidadEnFecha() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.doReturn(false).when(servicioCita).barberoTieneNovedadEnFechaCita(citaMock);
		Mockito.doReturn(true).when(servicioCita).barberoYaTieneCitaAsignadaEnFechaCita(citaMock);
		
		try {
			servicioCita.agendarCita(citaMock);
			fail("Deberia retornar excepcion por: " + ServicioCita.ERROR_BARBERO_SIN_DOSPINILIDAD_EN_FECHA);
		} catch(Exception ex) {
			assertTrue(ex instanceof RuntimeException);
			assertTrue(ex.getMessage().contains(ServicioCita.ERROR_BARBERO_SIN_DOSPINILIDAD_EN_FECHA));
		}
	}
	
	@Test
	public void agendarCitaConFechaPasada() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.doReturn(false).when(servicioCita).barberoTieneNovedadEnFechaCita(citaMock);
		Mockito.doReturn(false).when(servicioCita).barberoYaTieneCitaAsignadaEnFechaCita(citaMock);
		Mockito.doReturn(true).when(servicioCita).esFechaCitaMenorAlMomento(citaMock);
		
		try {
			servicioCita.agendarCita(citaMock);
			fail("Deberia retornar excepcion por: " + ServicioCita.ERROR_FECHA_PASADA);
		} catch(Exception ex) {
			assertTrue(ex instanceof RuntimeException);
			assertTrue(ex.getMessage().contains(ServicioCita.ERROR_FECHA_PASADA));
		}
	}
	
	@Test
	public void barberoNoTieneNovedades() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.when(repositorioNovedad.listarPorBarbero(Mockito.any())).thenReturn(null);
		Mockito.doReturn(true).when(servicioCita).listaNovedadesEsNullaOVacia(Mockito.any());
		
		boolean validacion = servicioCita.barberoTieneNovedadEnFechaCita(citaMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void barberoTieneNovedadesEnOtrasFechas() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		List<Novedad> novedadesMock = new ArrayList<Novedad>();
		novedadesMock.add(novedadMock);
		Mockito.when(repositorioNovedad.listarPorBarbero(Mockito.any())).thenReturn(novedadesMock);
		Mockito.doReturn(false).when(servicioCita).listaNovedadesEsNullaOVacia(Mockito.any());
		Mockito.doReturn(false).when(servicioCita).esFechaEnRango(citaMock.getFecha(), novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		
		boolean validacion = servicioCita.barberoTieneNovedadEnFechaCita(citaMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void barberoTieneNovedadEnFechaCita() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		List<Novedad> novedadesMock = new ArrayList<Novedad>();
		novedadesMock.add(novedadMock);
		Mockito.when(repositorioNovedad.listarPorBarbero(Mockito.any())).thenReturn(novedadesMock);
		Mockito.doReturn(false).when(servicioCita).listaNovedadesEsNullaOVacia(Mockito.any());
		Mockito.doReturn(true).when(servicioCita).esFechaEnRango(citaMock.getFecha(), novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		
		boolean validacion = servicioCita.barberoTieneNovedadEnFechaCita(citaMock);
		
		assertTrue(validacion);
	}
	
	@Test
	public void barberoNoTieneCitasAsignadas() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		Mockito.when(repositorioCita.retornar(Mockito.any())).thenReturn(null);
		Mockito.doReturn(true).when(servicioCita).listaCitasEsNullaOVacia(Mockito.any());
		
		boolean validacion = servicioCita.barberoYaTieneCitaAsignadaEnFechaCita(citaMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void barberoTieneCitasEnDiferentesFechas() {
		Cita citaCrearMock = CitaDataBuilder.aCitaDataBuilder().withId(11l).build();
		Cita citaConsultaMock = CitaDataBuilder.aCitaDataBuilder().withId(12l).build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaConsultaMock);
		Mockito.when(repositorioCita.retornar(Mockito.any())).thenReturn(listaCitasMock);
		Mockito.doReturn(false).when(servicioCita).listaCitasEsNullaOVacia(Mockito.any());
		Mockito.doReturn(false).when(servicioCita).esMismaFecha(citaCrearMock.getFecha(), citaConsultaMock.getFecha());
		
		boolean validacion = servicioCita.barberoYaTieneCitaAsignadaEnFechaCita(citaCrearMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void barberoYaTieneCitaAsignadaEnFechaCita() {
		Cita citaCrearMock = CitaDataBuilder.aCitaDataBuilder().withId(11l).build();
		Cita citaConsultaMock = CitaDataBuilder.aCitaDataBuilder().withId(12l).build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaConsultaMock);
		Mockito.when(repositorioCita.retornar(Mockito.any())).thenReturn(listaCitasMock);
		Mockito.doReturn(false).when(servicioCita).listaCitasEsNullaOVacia(Mockito.any());
		Mockito.doReturn(true).when(servicioCita).esMismaFecha(citaCrearMock.getFecha(), citaConsultaMock.getFecha());
		
		boolean validacion = servicioCita.barberoYaTieneCitaAsignadaEnFechaCita(citaCrearMock);
		
		assertTrue(validacion);
	}
	
	@Test
	public void listaCitasEsNulla() {
		List<Cita> listaCitasMock = null;
		
		boolean validacion = servicioCita.listaCitasEsNullaOVacia(listaCitasMock);
		
		assertTrue(validacion);
	}
	
	@Test
	public void listaCitasEsVacia() {
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		
		boolean validacion = servicioCita.listaCitasEsNullaOVacia(listaCitasMock);
		
		assertTrue(validacion);
	}
	
	@Test
	public void listaCitasNoEsNullaOVacia() {
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		
		boolean validacion = servicioCita.listaCitasEsNullaOVacia(listaCitasMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void listaNovedadesEsNulla() {
		List<Novedad> listaNovedadesMock = null;
		
		boolean validacion = servicioCita.listaNovedadesEsNullaOVacia(listaNovedadesMock);
		
		assertTrue(validacion);
	}	
	
	@Test
	public void listaNovedadesEsVacia() {
		List<Novedad> listaNovedadesMock = new ArrayList<Novedad>();
		
		boolean validacion = servicioCita.listaNovedadesEsNullaOVacia(listaNovedadesMock);
		
		assertTrue(validacion);
	}	
	
	@Test
	public void listaNovedadesNoEsNullaOVacia() {
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder().build();
		List<Novedad> listaNovedadesMock = new ArrayList<Novedad>();
		listaNovedadesMock.add(novedadMock);
		
		boolean validacion = servicioCita.listaNovedadesEsNullaOVacia(listaNovedadesMock);
		
		assertFalse(validacion);
	}
	
	@Test
	public void esMismaFecha() {
		Date fecha = new Date();
		
		boolean validacion = servicioCita.esMismaFecha(fecha, fecha);
		
		assertTrue(validacion);
	}
	
	@Test
	public void noEsMismaFecha() throws ParseException {
		Date fecha1 = formatter.parse("20191209 14:00");
		Date fecha2 = formatter.parse("20191209 14:01");
		
		boolean validacion = servicioCita.esMismaFecha(fecha1, fecha2);
		
		assertFalse(validacion);
	}
	
	@Test
	public void esFechaEnRango() throws ParseException {
		Date fecha = formatter.parse("20191209 14:00");
		Date min = formatter.parse("20191209 00:00");
		Date max = formatter.parse("20191209 23:59");
		
		boolean validacion = servicioCita.esFechaEnRango(fecha, min, max);
		
		assertTrue(validacion);
	}
	
	@Test
	public void esFechaEnRangoMismaMinimaFecha() throws ParseException {
		Date fecha = formatter.parse("20191209 00:00");
		Date min = formatter.parse("20191209 00:00");
		Date max = formatter.parse("20191209 23:59");
		
		boolean validacion = servicioCita.esFechaEnRango(fecha, min, max);
		
		assertTrue(validacion);
	}
	
	@Test
	public void esFechaEnRangoMismaMaximaFecha() throws ParseException {
		Date fecha = formatter.parse("20191209 23:59");
		Date min = formatter.parse("20191209 00:00");
		Date max = formatter.parse("20191209 23:59");
		
		boolean validacion = servicioCita.esFechaEnRango(fecha, min, max);
		
		assertTrue(validacion);
	}
	
	@Test
	public void esFechaEnRangoFechaMenorAFechaMinima() throws ParseException {
		Date fecha = formatter.parse("20191208 23:59");
		Date min = formatter.parse("20191209 00:00");
		Date max = formatter.parse("20191209 23:59");
		
		boolean validacion = servicioCita.esFechaEnRango(fecha, min, max);
		
		assertFalse(validacion);
	}
	
	@Test
	public void esFechaEnRangoFechaMayorAFechaMaxima() throws ParseException {
		Date fecha = formatter.parse("20191210 00:01");
		Date min = formatter.parse("20191209 00:00");
		Date max = formatter.parse("20191209 23:59");
		
		boolean validacion = servicioCita.esFechaEnRango(fecha, min, max);
		
		assertFalse(validacion);
	}
	
	@Test
	public void esFechaCitaMenorAlMomento() {
		Date ahora = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ahora);
		cal.add(Calendar.DATE, -1);
		Date fecha = cal.getTime();
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().withFecha(fecha).build();
		
		boolean validacion = servicioCita.esFechaCitaMenorAlMomento(citaMock);
		
		assertTrue(validacion);
	}
	
	@Test
	public void laFechaCitaNoEsMenorAlMomento() {
		Date ahora = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(ahora);
		cal.add(Calendar.DATE, 1);
		Date fecha = cal.getTime();
		Cita citaMock = CitaDataBuilder.aCitaDataBuilder().withFecha(fecha).build();
		
		boolean validacion = servicioCita.esFechaCitaMenorAlMomento(citaMock);
		
		assertFalse(validacion);
	}
}
