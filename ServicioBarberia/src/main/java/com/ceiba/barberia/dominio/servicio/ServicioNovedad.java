package com.ceiba.barberia.dominio.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;

@Service
public class ServicioNovedad {
	
	private RepositorioNovedad repositorioNovedad;
	
	public ServicioNovedad(RepositorioNovedad repositorioNovedad) {
		this.repositorioNovedad = repositorioNovedad;
	}
	
	public Novedad crear(Novedad novedad) {
		return repositorioNovedad.crear(novedad);
	}
	
	public List<Novedad> listar() {
		return repositorioNovedad.retornar();
	}
}
