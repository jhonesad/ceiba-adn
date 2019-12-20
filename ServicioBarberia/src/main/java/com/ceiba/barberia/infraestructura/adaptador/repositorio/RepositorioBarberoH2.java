package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;
import com.ceiba.barberia.infraestructura.adaptador.BarberoRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;

@Repository
public class RepositorioBarberoH2 implements RepositorioBarbero {
	
	@Autowired
	private EntityManager entityManager;
	
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
		return retornarListaBarberos(listaBarberoEntidad);
	}

	@Override
	public List<Barbero> listarPorNombre(String nombre) {
		TypedQuery<BarberoEntidad> query = getEntityManager()
				.createQuery("SELECT b FROM BarberoEntidad b WHERE b.nombre LIKE :nombre", BarberoEntidad.class);
		query.setParameter("nombre", "%"+nombre+"%");
		List<BarberoEntidad> listaBarberoEntidad = query.getResultList();
		return retornarListaBarberos(listaBarberoEntidad);
	}
	
	protected List<Barbero> retornarListaBarberos(List<BarberoEntidad> listaBarberoEntidad) {
		List<Barbero> listaBarbero = new ArrayList<>();
		for(BarberoEntidad barberoEntidad : listaBarberoEntidad) {
			listaBarbero.add(modelMapper.map(barberoEntidad, Barbero.class));
		}
		return listaBarbero;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
