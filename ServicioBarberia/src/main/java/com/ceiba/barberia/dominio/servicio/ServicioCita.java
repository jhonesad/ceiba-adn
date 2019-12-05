package com.ceiba.barberia.dominio.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;

@Service
public class ServicioCita {
	
	private RepositorioCita repositorioCita;
	
	public ServicioCita(RepositorioCita repositorioCita) {
		this.repositorioCita = repositorioCita;
	}
	
	public Cita agendarCita(Cita cita) {
		//Realizar validaciones para agendar la cita y guardar o retornar excepcion si no es posible
		return this.repositorioCita.crear(cita);
	}
	
	public List<Cita> listarCitas() {
		return this.repositorioCita.retornar();
	}
}
