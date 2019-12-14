package com.ceiba.barberia.dominio.servicio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.exception.BarberiaBusinessLogicException;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;

@Service
public class ServicioNovedad {
	
	private RepositorioNovedad repositorioNovedad;
	private RepositorioCita repositorioCita;
	
	protected static final String ERROR_FECHA_INICIO_PASADA = "La fecha y hora de inicio ya ha pasado. Debe seleccionar una fecha y hora mayor al momento.";
	protected static final String ERROR_FECHA_FIN_PASADA = "La fecha y hora de fin ya ha pasado. Debe seleccionar una fecha y hora mayor al momento.";
	protected static final String ERROR_FECHAS_FESTIVO_DIFERENTE = "Las fechas del día festivo deben ser el mismo día.";
	protected static final String ERROR_FECHA_FESTIVO_PASADA = "La fecha ya ha pasado. Debe seleccionar una fecha mayor al momento.";
	protected static final String ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO = "La fecha y hora de fin de la novedad deben ser superiores a la fecha y hora de inicio.";
	protected static final String ERROR_FESTIVO_DUPLICADO = "La fecha ya se encuentra registrada como día festivo en el sistema.";
	protected static final String ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO = "El barbero ya presenta novedades en el rango de fecha/tiempo que esta tratando de guardar esta novedad.";
	protected static final String ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO = "El barbero ya tiene citas pendientes en el rango de fecha/tiempo que esta tratando de guardar esta novedad.";
	
	public ServicioNovedad(RepositorioNovedad repositorioNovedad, RepositorioCita repositorioCita) {
		this.repositorioNovedad = repositorioNovedad;
		this.repositorioCita = repositorioCita;
	}
	
	public Novedad crear(Novedad novedad) {		
		validarYPrepararNovedad(novedad);
		return repositorioNovedad.crear(novedad);
	}

	public List<Novedad> listar() {
		return repositorioNovedad.retornar();
	}
	
	public List<Novedad> listarFestivos(Date fechaMinima) {
		return repositorioNovedad.listarFestivos(fechaMinima);
	}
	
	protected void validarYPrepararNovedad(Novedad novedad) {		
		if(novedad.getFestivo()) {
			inicioYFinFestivoNoEsMismaFecha(novedad.getFechaInicio(), novedad.getFechaFin());
			setTiempoFestivo(novedad);
			esFechaMenorAlMomento(novedad.getFechaFin(), false, ERROR_FECHA_FESTIVO_PASADA);
			festivoDuplicado(novedad.getFechaInicio());
		} else {
			esFechaMenorAlMomento(novedad.getFechaInicio(), true, ERROR_FECHA_INICIO_PASADA);
			esFechaMenorAlMomento(novedad.getFechaFin(), true, ERROR_FECHA_FIN_PASADA);
			fechaInicioMayorAFechaFin(novedad.getFechaInicio(), novedad.getFechaFin());
			novedadEnRangoFechaNovedadBarbero(novedad);
			novedadEnRangoFechaCitaBarbero(novedad);
		}
	}
	
	protected void inicioYFinFestivoNoEsMismaFecha(Date inicio, Date fin) {
		if(getFechaWithSpecificTime(inicio,0,0,0,0).compareTo(getFechaWithSpecificTime(fin, 0,0,0,0)) != 0 )
			throw new BarberiaBusinessLogicException(ERROR_FECHAS_FESTIVO_DIFERENTE);
	}
	
	protected void fechaInicioMayorAFechaFin(Date inicio, Date fin) {
		if(fin.compareTo(inicio) <= 0)
			throw new BarberiaBusinessLogicException(ERROR_FECHA_FIN_SUPERIOR_FECHA_INICIO);
	}
	
	protected void festivoDuplicado(Date fechaFestivo) {
		Novedad festivo = repositorioNovedad.consultarFestivo(fechaFestivo);
		if(festivo != null)
			throw new BarberiaBusinessLogicException(ERROR_FESTIVO_DUPLICADO);
	}
	
	protected void setTiempoFestivo(Novedad festivo) {
		festivo.setFechaInicio(getFechaWithSpecificTime(festivo.getFechaInicio(), 0, 0, 0, 0));
		festivo.setFechaFin(getFechaWithSpecificTime(festivo.getFechaFin(), 23, 59, 59, 999));
	}
	
	protected void novedadEnRangoFechaNovedadBarbero(Novedad novedad) {
		List<Novedad> novedadesBarbero = repositorioNovedad.listarPorBarbero(novedad.getBarbero().getId());
		
		boolean esNovedadEnRangoFechaNovedadBarbero = novedadesBarbero.stream().anyMatch(nov -> 
			(novedad.getFechaInicio().compareTo(nov.getFechaInicio()) >= 0 && novedad.getFechaInicio().compareTo(nov.getFechaFin()) <= 0) ||
					(novedad.getFechaFin().compareTo(nov.getFechaInicio()) >= 0 && novedad.getFechaFin().compareTo(nov.getFechaFin()) <= 0)
		);
		
		if(esNovedadEnRangoFechaNovedadBarbero)
			throw new BarberiaBusinessLogicException(ERROR_NOVEDAD_ENTRE_FECHAS_NOVEDADES_BARBERO);
	}
	
	protected void novedadEnRangoFechaCitaBarbero(Novedad novedad) {
		List<Cita> citasBarbero = repositorioCita.retornar(novedad.getBarbero().getId());
		
		boolean esNovedadEnRangoFechaCitaBarbero = citasBarbero.stream().anyMatch(cita -> 
			(cita.getFecha().compareTo(novedad.getFechaInicio()) >= 0 && cita.getFecha().compareTo(novedad.getFechaFin()) <= 0)
		);
		
		if(esNovedadEnRangoFechaCitaBarbero)
			throw new BarberiaBusinessLogicException(ERROR_NOVEDAD_ENTRE_FECHAS_CITAS_BARBERO);
	}
	
	protected void esFechaMenorAlMomento(Date fecha, boolean compararTiempo, String mensajeError) {
		Date ahora = getAhora();
		
		if(compararTiempo) {
			if(fecha.compareTo(ahora) <= 0) 
				throw new BarberiaBusinessLogicException(mensajeError);
		} else {
			if(getFechaWithSpecificTime(fecha,0,0,0,0).compareTo(getFechaWithSpecificTime(ahora,0,0,0,0)) <= 0 ) 
				throw new BarberiaBusinessLogicException(mensajeError);
		}
		
	}
	
	protected Date getFechaWithSpecificTime(Date fecha, int hora, int minutos, int segundos, int mil) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.HOUR_OF_DAY, hora);
	    calendar.set(Calendar.MINUTE, minutos);
	    calendar.set(Calendar.SECOND, segundos);
	    calendar.set(Calendar.MILLISECOND, mil);
	    
	    return calendar.getTime();
	}
	
	protected Date getAhora() {
		return new Date();
	}
}
