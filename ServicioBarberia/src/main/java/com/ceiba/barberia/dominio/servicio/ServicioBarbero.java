package com.ceiba.barberia.dominio.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;

@Service
public class ServicioBarbero {
	
	private RepositorioBarbero repositorioBarbero;
	
	public ServicioBarbero(RepositorioBarbero repositorioBarbero) {
		this.repositorioBarbero = repositorioBarbero;
	}
	
	public Barbero crear(Barbero barbero) {
		return this.repositorioBarbero.crear(barbero);
	}
	
	public List<Barbero> listar() {
		return repositorioBarbero.listar();
	}
}
