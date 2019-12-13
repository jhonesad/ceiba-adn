package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;

@Repository
public class RepositorioBarberoH2 implements RepositorioBarbero {
	
	private final BarberoRepositorioJPA barberoRepositorioJPA;
	private ModelMapper modelMapper = new ModelMapper();
	
	public RepositorioBarberoH2(BarberoRepositorioJPA barberoRepositorioJPA) {
		this.barberoRepositorioJPA = barberoRepositorioJPA;
	}

	@Override
	public Barbero crear(Barbero barbero) {
		BarberoEntidad barberoEntidad = modelMapper.map(barbero, BarberoEntidad.class);
		barberoRepositorioJPA.save(barberoEntidad);
		barbero.setId(barberoEntidad.getId());
		return barbero;
	}

	@Override
	public List<Barbero> listar() {
		List<BarberoEntidad> listaBarberoEntidad = barberoRepositorioJPA.findAll();
		List<Barbero> listaBarbero = new ArrayList<>();
		for(BarberoEntidad barberoEntidad : listaBarberoEntidad) {
			listaBarbero.add(modelMapper.map(barberoEntidad, Barbero.class));
		}
		return listaBarbero;
	}

}
