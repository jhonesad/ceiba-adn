package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.infraestructura.adaptador.CitaRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidad;

@Repository
public class RepositorioCitaH2 implements RepositorioCita {
	
	@Autowired
	private EntityManager entityManager;
	
	private final CitaRepositorioJPA citaRepositorioJPA;
	private ModelMapper modelMapper = new ModelMapper();
	
	public RepositorioCitaH2(CitaRepositorioJPA citaRepositorioJPA) {
		this.citaRepositorioJPA = citaRepositorioJPA;
	}

	@Override
	public Cita crear(Cita cita) {
		CitaEntidad citaEntidad = modelMapper.map(cita, CitaEntidad.class);
		citaRepositorioJPA.save(citaEntidad);
		cita.setId(citaEntidad.getId());
		return cita;
	}

	@Override
	public List<Cita> retornar() {
		List<CitaEntidad> listaCitaEntidad = citaRepositorioJPA.findAll();
		return retornarListaCitas(listaCitaEntidad);
	}

	@Override
	public List<Cita> retornar(Long idBarbero) {
		TypedQuery<CitaEntidad> query = entityManager
				.createQuery("SELECT c FROM CitaEntidad c WHERE c.barbero.id = :idBarbero", CitaEntidad.class);
		query.setParameter("idBarbero", idBarbero);
		List<CitaEntidad> listaCitaEntidad = query.getResultList();
		
		return retornarListaCitas(listaCitaEntidad);
	}
	
	private List<Cita> retornarListaCitas(List<CitaEntidad> listaCitaEntidad) {
		List<Cita> listaCitas = new ArrayList<Cita>();
		for(CitaEntidad citaEntidad : listaCitaEntidad) {
			listaCitas.add(modelMapper.map(citaEntidad, Cita.class));
		}
		return listaCitas;
	}
}
