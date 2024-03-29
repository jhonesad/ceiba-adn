package com.ceiba.barberia.dominio.puerto.repositorio;

import java.util.Date;
import java.util.List;

import com.ceiba.barberia.dominio.entidades.Novedad;

public interface RepositorioNovedad {
	
	Novedad crear(Novedad novedad);
	
	List<Novedad> retornar();
	
	List<Novedad> listarPorBarbero(Long idBarbero);
	
	List<Novedad> listarFestivos(Date fechaMinima);
	
	Novedad consultarFestivo(Date fechaFestivo);
}
