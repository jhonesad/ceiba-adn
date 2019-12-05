package com.ceiba.barberia.dominio.puerto.repositorio;

import java.util.List;

import com.ceiba.barberia.dominio.entidades.Novedad;

public interface RepositorioNovedad {
	
	Novedad crear(Novedad novedad);
	
	List<Novedad> retornar();
}
