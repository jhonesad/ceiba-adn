package com.ceiba.barberia.dominio.servicio;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.exception.BarberiaBusinessLogicException;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;
import com.ceiba.barberia.testdatabuilder.BarberoDataBuilder;
import com.ceiba.barberia.testdatabuilder.CitaDataBuilder;
import com.ceiba.barberia.testdatabuilder.NovedadDataBuilder;

public class ServicioNovedadTest {

	private RepositorioNovedad repositorioNovedad;
	private RepositorioCita repositorioCita;
	private ServicioNovedad servicioNovedad;
	
	private SimpleDateFormat dateTimeFormatter;
	
	@Before
	public void setUp() {
		repositorioNovedad = Mockito.mock(RepositorioNovedad.class);
		repositorioCita = Mockito.mock(RepositorioCita.class);
		servicioNovedad = Mockito.spy(new ServicioNovedad(repositorioNovedad, repositorioCita));
		
		dateTimeFormatter = new SimpleDateFormat("yyyyMMdd HH:mm");
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
		Mockito.doNothing().when(servicioNovedad).validarYPrepararNovedad(novedadMock);
		
		Novedad novedad = servicioNovedad.crear(novedadMock);
		
		assertNotNull(novedad);
		assertEquals(novedadMock.getId(), novedad.getId());
	}
	
	@Test
	public void validarYPrepararNovedadFestivo() {
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withBarbero(null)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withFestivo(true)
				.withDescripcion("test").build();
		
		Mockito.doNothing().when(servicioNovedad).inicioYFinFestivoNoEsMismaFecha(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.doNothing().when(servicioNovedad).setTiempoFestivo(novedadMock);
		Mockito.doNothing().when(servicioNovedad).esFechaMenorAlMomento(novedadMock.getFechaFin(), false, ServicioNovedad.ERROR_FECHA_FESTIVO_PASADA);
		Mockito.doNothing().when(servicioNovedad).festivoDuplicado(novedadMock.getFechaInicio());
		
		servicioNovedad.validarYPrepararNovedad(novedadMock);
		
		Mockito.verify(servicioNovedad, Mockito.times(1)).inicioYFinFestivoNoEsMismaFecha(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.verify(servicioNovedad, Mockito.times(1)).setTiempoFestivo(novedadMock);
		Mockito.verify(servicioNovedad, Mockito.times(1)).esFechaMenorAlMomento(novedadMock.getFechaFin(), false, ServicioNovedad.ERROR_FECHA_FESTIVO_PASADA);
		Mockito.verify(servicioNovedad, Mockito.times(1)).festivoDuplicado(novedadMock.getFechaInicio());
		
		Mockito.verify(servicioNovedad, Mockito.never()).esFechaMenorAlMomento(novedadMock.getFechaInicio(), true, ServicioNovedad.ERROR_FECHA_INICIO_PASADA);
		Mockito.verify(servicioNovedad, Mockito.never()).esFechaMenorAlMomento(novedadMock.getFechaFin(), true, ServicioNovedad.ERROR_FECHA_FIN_PASADA);
		Mockito.verify(servicioNovedad, Mockito.never()).fechaInicioMayorAFechaFin(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.verify(servicioNovedad, Mockito.never()).novedadEnRangoFechaNovedadBarbero(novedadMock);
		Mockito.verify(servicioNovedad, Mockito.never()).novedadEnRangoFechaCitaBarbero(novedadMock);
	}
	
	@Test
	public void validarYPrepararNovedadBarbero() {
		Barbero barberoMock = BarberoDataBuilder.aBarberoDataBuilder()
				.withId(1l)
				.withNombre("barbero test").build();
		
		Novedad novedadMock = NovedadDataBuilder.aNovedadDataBuilder()
				.withId(null)
				.withBarbero(barberoMock)
				.withFechaInicio(new Date())
				.withFechaFin(new Date())
				.withFestivo(false)
				.withDescripcion("test").build();
		
		Mockito.doNothing().when(servicioNovedad).esFechaMenorAlMomento(novedadMock.getFechaInicio(), true, ServicioNovedad.ERROR_FECHA_INICIO_PASADA);
		Mockito.doNothing().when(servicioNovedad).esFechaMenorAlMomento(novedadMock.getFechaFin(), true, ServicioNovedad.ERROR_FECHA_FIN_PASADA);
		Mockito.doNothing().when(servicioNovedad).fechaInicioMayorAFechaFin(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.doNothing().when(servicioNovedad).novedadEnRangoFechaNovedadBarbero(novedadMock);
		Mockito.doNothing().when(servicioNovedad).novedadEnRangoFechaCitaBarbero(novedadMock);
		
		servicioNovedad.validarYPrepararNovedad(novedadMock);
		
		Mockito.verify(servicioNovedad, Mockito.times(1)).esFechaMenorAlMomento(novedadMock.getFechaInicio(), true, ServicioNovedad.ERROR_FECHA_INICIO_PASADA);
		Mockito.verify(servicioNovedad, Mockito.times(1)).esFechaMenorAlMomento(novedadMock.getFechaFin(), true, ServicioNovedad.ERROR_FECHA_FIN_PASADA);
		Mockito.verify(servicioNovedad, Mockito.times(1)).fechaInicioMayorAFechaFin(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.verify(servicioNovedad, Mockito.times(1)).novedadEnRangoFechaNovedadBarbero(novedadMock);
		Mockito.verify(servicioNovedad, Mockito.times(1)).novedadEnRangoFechaCitaBarbero(novedadMock);
		
		Mockito.verify(servicioNovedad, Mockito.never()).inicioYFinFestivoNoEsMismaFecha(novedadMock.getFechaInicio(), novedadMock.getFechaFin());
		Mockito.verify(servicioNovedad, Mockito.never()).setTiempoFestivo(novedadMock);
		Mockito.verify(servicioNovedad, Mockito.never()).esFechaMenorAlMomento(novedadMock.getFechaFin(), false, ServicioNovedad.ERROR_FECHA_FESTIVO_PASADA);
		Mockito.verify(servicioNovedad, Mockito.never()).festivoDuplicado(novedadMock.getFechaInicio());
	}
	
	@Test
	public void inicioYFinFestivoNoEsMismaFechaDiasDiferentes() throws Exception {
		Date inicio = dateTimeFormatter.parse("20191214 08:30");
		Date fin = dateTimeFormatter.parse("20191215 23:59");
		
		try {
			servicioNovedad.inicioYFinFestivoNoEsMismaFecha(inicio, fin);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_FECHAS_FESTIVO_DIFERENTE);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_FECHAS_FESTIVO_DIFERENTE));
		}
	}
	
	@Test
	public void inicioYFinFestivoNoEsMismaFechaMismoDiaHoraDiferente() throws Exception {
		Date inicio = dateTimeFormatter.parse("20191214 08:30");
		Date fin = dateTimeFormatter.parse("20191214 23:59");
		
		try {
			servicioNovedad.inicioYFinFestivoNoEsMismaFecha(inicio, fin);
			
			Mockito.verify(servicioNovedad, Mockito.times(1)).getFechaWithSpecificTime(inicio,0,0,0,0);
			Mockito.verify(servicioNovedad, Mockito.times(1)).getFechaWithSpecificTime(fin,0,0,0,0);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_FECHAS_FESTIVO_DIFERENTE);
		}
	}
	
	@Test
	public void fechaInicioMayorAFechaFin_FechasIguales() throws Exception {
		Date inicio = dateTimeFormatter.parse("20191214 08:30");
		Date fin = dateTimeFormatter.parse("20191214 08:30");
		
		try {
			servicioNovedad.fechaInicioMayorAFechaFin(inicio, fin);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO));
		}
	}
	
	@Test
	public void fechaInicioMayorAFechaFin_InicioMayor() throws Exception {
		Date inicio = dateTimeFormatter.parse("20191214 08:31");
		Date fin = dateTimeFormatter.parse("20191214 08:30");
		
		try {
			servicioNovedad.fechaInicioMayorAFechaFin(inicio, fin);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO));
		}
	}
	
	@Test
	public void fechaInicioMayorAFechaFin_InicioMenor() throws Exception {
		Date inicio = dateTimeFormatter.parse("20191214 08:29");
		Date fin = dateTimeFormatter.parse("20191214 08:30");
		
		try {
			servicioNovedad.fechaInicioMayorAFechaFin(inicio, fin);
			assertNotNull(inicio);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO);
		}
	}
	
	@Test
	public void festivoDuplicado() {
		Novedad festivo = NovedadDataBuilder.aNovedadDataBuilder().build();
		Date fechaFestivo = new Date();
		Mockito.when(repositorioNovedad.consultarFestivo(fechaFestivo)).thenReturn(festivo);
		
		try {
			servicioNovedad.festivoDuplicado(fechaFestivo);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_FESTIVO_DUPLICADO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_FESTIVO_DUPLICADO));
		}
	}
	
	@Test
	public void festivoNoDuplicado() {
		Date fechaFestivo = new Date();
		Mockito.when(repositorioNovedad.consultarFestivo(fechaFestivo)).thenReturn(null);
		
		try {
			servicioNovedad.festivoDuplicado(fechaFestivo);
			assertNotNull(fechaFestivo);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_FESTIVO_DUPLICADO);
		}
	}
	
	@Test
	public void setTiempoFestivo() {
		Novedad festivo = Mockito.spy(NovedadDataBuilder.aNovedadDataBuilder()
				.withFechaInicio(new Date())
				.withFechaFin(new Date()).build());
		
		servicioNovedad.setTiempoFestivo(festivo);
		
		Mockito.verify(festivo, Mockito.times(1)).setFechaInicio(Mockito.any(Date.class));
		Mockito.verify(festivo, Mockito.times(1)).setFechaFin(Mockito.any(Date.class));
		
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(festivo.getFechaInicio());
		assertEquals(0, calendarInicio.get(Calendar.HOUR_OF_DAY));
		assertEquals(0, calendarInicio.get(Calendar.MINUTE));
		assertEquals(0, calendarInicio.get(Calendar.SECOND));
		assertEquals(0, calendarInicio.get(Calendar.MILLISECOND));
		
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(festivo.getFechaFin());
		assertEquals(23, calendarFin.get(Calendar.HOUR_OF_DAY));
		assertEquals(59, calendarFin.get(Calendar.MINUTE));
		assertEquals(59, calendarFin.get(Calendar.SECOND));
		assertEquals(999, calendarFin.get(Calendar.MILLISECOND));
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FInicioEntreFechasNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 12:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 16:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FInicioIgualAFechaInicioNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 16:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FInicioIgualAFechaFinNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 16:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FFinEntreFechasNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 09:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 12:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FFinIgualAFechaInicioNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 09:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 11:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FFinIgualAFechaFinNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 09:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_CubreRangoFechasNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 09:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_FueraDeRangoFechasNovedades() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:01"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Novedad novedad1 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 08:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 10:00")).build();
		Novedad novedad2 = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 11:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Novedad> novedadesBarbero = new ArrayList<>();
		novedadesBarbero.add(novedad1);
		novedadesBarbero.add(novedad2);
		
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			assertNotNull(nuevaNovedad);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		}
	}
	
	@Test
	public void novedadEnRangoFechaNovedadBarbero_BarberoSinNovedades() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:01"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		List<Novedad> novedadesBarbero = new ArrayList<>();
		Mockito.when(repositorioNovedad.listarPorBarbero(barberoNovedad.getId())).thenReturn(novedadesBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaNovedadBarbero(nuevaNovedad);
			assertNotNull(nuevaNovedad);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
		}
	}
	
	@Test
	public void novedadEnRangoFechaCitaBarbero_CitaEntreFechasNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:01"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Cita cita1 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 10:00")).build();
		Cita cita2 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 13:30")).build();
		List<Cita> citasBarbero = new ArrayList<>();
		citasBarbero.add(cita1);
		citasBarbero.add(cita2);
		
		Mockito.when(repositorioCita.retornar(barberoNovedad.getId())).thenReturn(citasBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaCitaBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaCitaBarbero_FCitaIgualFinicioNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Cita cita1 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 10:00")).build();
		Cita cita2 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 13:00")).build();
		List<Cita> citasBarbero = new ArrayList<>();
		citasBarbero.add(cita1);
		citasBarbero.add(cita2);
		
		Mockito.when(repositorioCita.retornar(barberoNovedad.getId())).thenReturn(citasBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaCitaBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaCitaBarbero_FCitaIgualFFinNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Cita cita1 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 10:00")).build();
		Cita cita2 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 14:00")).build();
		List<Cita> citasBarbero = new ArrayList<>();
		citasBarbero.add(cita1);
		citasBarbero.add(cita2);
		
		Mockito.when(repositorioCita.retornar(barberoNovedad.getId())).thenReturn(citasBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaCitaBarbero(nuevaNovedad);
			fail("Deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO));
		}
	}
	
	@Test
	public void novedadEnRangoFechaCitaBarbero_CitasFueraRangoFechasNovedad() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:00"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		Cita cita1 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 10:00")).build();
		Cita cita2 = CitaDataBuilder.aCitaDataBuilder()
				.withBarbero(barberoNovedad)
				.withFecha(dateTimeFormatter.parse("20191214 14:30")).build();
		List<Cita> citasBarbero = new ArrayList<>();
		citasBarbero.add(cita1);
		citasBarbero.add(cita2);
		
		Mockito.when(repositorioCita.retornar(barberoNovedad.getId())).thenReturn(citasBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaCitaBarbero(nuevaNovedad);
			assertNotNull(nuevaNovedad);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
		}
	}
	
	@Test
	public void novedadEnRangoFechaCitaBarbero_BarberoSinCitas() throws Exception {
		Barbero barberoNovedad = BarberoDataBuilder.aBarberoDataBuilder().build();
		
		Novedad nuevaNovedad = NovedadDataBuilder.aNovedadDataBuilder()
				.withBarbero(barberoNovedad)
				.withFechaInicio(dateTimeFormatter.parse("20191214 13:01"))
				.withFechaFin(dateTimeFormatter.parse("20191214 14:00")).build();
		
		List<Cita> citasBarbero = new ArrayList<>();		
		Mockito.when(repositorioCita.retornar(barberoNovedad.getId())).thenReturn(citasBarbero);
		
		try {
			servicioNovedad.novedadEnRangoFechaCitaBarbero(nuevaNovedad);
			assertNotNull(nuevaNovedad);
		} catch (Exception ex) {
			fail("No deberia fallar por: " + ServicioNovedad.ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_CompararTiempo_UnMinutoAntes() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191214 18:59");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, true, mensajeError);
			fail("Deberia fallar");
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(mensajeError));
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_CompararTiempo_FechasIguales() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191214 19:00");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, true, mensajeError);
			fail("Deberia fallar");
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(mensajeError));
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_CompararTiempo_FechaMayor() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191214 19:01");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, true, mensajeError);
			assertNotNull(fechaComparar);
		} catch (Exception ex) {
			fail("No deberia fallar");
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_NoCompararTiempo_UnDiaAntes() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191213 18:59");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, false, mensajeError);
			fail("Deberia fallar");
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(mensajeError));
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_NoCompararTiempo_FechasIguales() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191214 20:00");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, false, mensajeError);
			fail("Deberia fallar");
		} catch (Exception ex) {
			assertTrue(ex instanceof BarberiaBusinessLogicException);
			assertTrue(ex.getMessage().contains(mensajeError));
		}
	}
	
	@Test
	public void esFechaMenorAlMomento_NoCompararTiempo_FechaMayor() throws Exception {
		Date ahora = dateTimeFormatter.parse("20191214 19:00");
		Date fechaComparar = dateTimeFormatter.parse("20191215 18:01");
		String mensajeError = "Error";
		Mockito.doReturn(ahora).when(servicioNovedad).getAhora();
		
		try {
			servicioNovedad.esFechaMenorAlMomento(fechaComparar, false, mensajeError);
			assertNotNull(fechaComparar);
		} catch (Exception ex) {
			fail("No deberia fallar");
		}
	}
	
	@Test
	public void testGetAhora() {
		assertNotNull(servicioNovedad.getAhora());
		assertTrue(servicioNovedad.getAhora() instanceof Date);
	}
	
	@Test
	public void testGetFechaWithSpecificTime() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date fecha = new Date();
		int horas = 9;
		int minutos = 26;
		int segundos = 12;
		int milisegundos = 654;
		
		Date fechaConTiempoEspecifico = servicioNovedad.getFechaWithSpecificTime(fecha, horas, minutos, segundos, milisegundos);
		
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(fechaConTiempoEspecifico);
		assertEquals(formatter.format(fecha), formatter.format(fechaConTiempoEspecifico));
		assertEquals(horas, calendarInicio.get(Calendar.HOUR_OF_DAY));
		assertEquals(minutos, calendarInicio.get(Calendar.MINUTE));
		assertEquals(segundos, calendarInicio.get(Calendar.SECOND));
		assertEquals(milisegundos, calendarInicio.get(Calendar.MILLISECOND));
	}
}
