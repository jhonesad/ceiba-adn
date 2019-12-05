package com.ceiba.barberia.dominio.puerto.repositorio;

import java.util.List;

import com.ceiba.barberia.dominio.entidades.Barbero;

public interface RepositorioBarbero {
	
	Barbero crear(Barbero barbero);
	
	List<Barbero> listar();
}
