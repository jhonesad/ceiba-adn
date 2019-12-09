package com.ceiba.barberia.dominio.puerto.repositorio;

import java.util.List;

import com.ceiba.barberia.dominio.entidades.Cita;

public interface RepositorioCita {
	
	Cita crear(Cita cita);
	
	List<Cita> retornar();
	
	List<Cita> retornar(Long idBarbero);
}
