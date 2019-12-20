package com.ceiba.barberia.dominio.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.exception.BarberiaBusinessLogicException;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;

@Service
public class ServicioCita {
	
	private RepositorioCita repositorioCita;
	private RepositorioNovedad repositorioNovedad;
	
	protected static final String ERROR_BARBERO_CON_NOVEDAD_EN_FECHA = "El barbero no tiene disponibilidad para la fecha de la cita";
	protected static final String ERROR_BARBERO_SIN_DOSPINILIDAD_EN_FECHA = "La fecha de la cita ya se encuentra ocupada";
	protected static final String ERROR_FECHA_PASADA = "La fecha de la cita ya ha pasado";
	
	public ServicioCita(RepositorioCita repositorioCita, RepositorioNovedad repositorioNovedad) {
		this.repositorioCita = repositorioCita;
		this.repositorioNovedad = repositorioNovedad;
	}
	
	public List<Cita> listar() {
		return this.repositorioCita.retornar();
	}
	
	public Cita agendar(Cita cita) {
		if(barberoTieneNovedadEnFechaCita(cita)) {
			throw new BarberiaBusinessLogicException(ERROR_BARBERO_CON_NOVEDAD_EN_FECHA);
		}
		
		if(barberoYaTieneCitaAsignadaEnFechaCita(cita)) {
			throw new BarberiaBusinessLogicException(ERROR_BARBERO_SIN_DOSPINILIDAD_EN_FECHA);
		}
		
		if(esFechaCitaMenorAlMomento(cita)) {
			throw new BarberiaBusinessLogicException(ERROR_FECHA_PASADA);
		}
		
		return this.repositorioCita.crear(cita);
	}
	
	
	protected boolean barberoTieneNovedadEnFechaCita(Cita cita) {
		List<Novedad> novedadesBarbero = repositorioNovedad.listarPorBarbero(cita.getBarbero().getId());
		boolean tieneNovedadEnFechaCita = false;
		if(!listaNovedadesEsNullaOVacia(novedadesBarbero)) {
			for(Novedad novedad : novedadesBarbero) {
				if(esFechaEnRango(cita.getFecha(), novedad.getFechaInicio(), novedad.getFechaFin())) {
					tieneNovedadEnFechaCita = true;
					break;
				}
			}
		}
		
		return tieneNovedadEnFechaCita;
	}
	
	protected boolean barberoYaTieneCitaAsignadaEnFechaCita(Cita cita) {
		List<Cita> listaCitasBarbero = repositorioCita.retornar(cita.getBarbero().getId());
		boolean tieneCitaAsignadaEnFechaCita = false;
		if(!listaCitasEsNullaOVacia(listaCitasBarbero)) {
			for(Cita c : listaCitasBarbero) {
				if(esMismaFecha(cita.getFecha(), c.getFecha())) {
					tieneCitaAsignadaEnFechaCita = true;
					break;
				}
			}
		}
		
		return tieneCitaAsignadaEnFechaCita;
	}
	
	protected boolean esFechaCitaMenorAlMomento(Cita cita) {
		Date ahora = new Date();
		return cita.getFecha().compareTo(ahora) <= 0;
	}
	
	protected boolean listaCitasEsNullaOVacia(List<Cita> listaCitas) {
		return listaCitas == null || listaCitas.isEmpty();
	}
	
	protected boolean listaNovedadesEsNullaOVacia(List<Novedad> listaNovedades) {
		return listaNovedades == null || listaNovedades.isEmpty();
	}
	
	protected boolean esMismaFecha(Date fecha1, Date fecha2) {
		return fecha1.compareTo(fecha2) == 0;
	}
	
	protected boolean esFechaEnRango(Date fecha, Date fechaMinima, Date fechaMaxima) {
		return fecha.compareTo(fechaMinima) >= 0 && fecha.compareTo(fechaMaxima) <= 0;
	}
}
