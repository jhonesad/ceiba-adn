package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioCita;
import com.ceiba.barberia.infraestructura.adaptador.CitaRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidad;
import com.ceiba.barberia.infraestructura.entidad.CitaEntidadPK;

@Repository
public class RepositorioCitaH2 implements RepositorioCita {
	
	private final CitaRepositorioJPA citaRepositorioJPA;
	private ModelMapper modelMapper = new ModelMapper();
	
	public RepositorioCitaH2(CitaRepositorioJPA citaRepositorioJPA) {
		this.citaRepositorioJPA = citaRepositorioJPA;
	}

	@Override
	public Cita crear(Cita cita) {
		CitaEntidad citaEntidad = modelMapper.map(cita, CitaEntidad.class);
		CitaEntidadPK pk = new CitaEntidadPK();
		pk.setFecha(cita.getFecha());
		pk.setBarbero(modelMapper.map(cita.getBarbero(), BarberoEntidad.class));
		citaEntidad.setPk(pk);
		
		citaRepositorioJPA.save(citaEntidad);
		return cita;
	}

	@Override
	public List<Cita> retornar() {
		List<CitaEntidad> listaCitaEntidad = citaRepositorioJPA.findAll();
		List<Cita> listaCitas = new ArrayList<Cita>();
		for(CitaEntidad citaEntidad : listaCitaEntidad) {
			Cita cita = modelMapper.map(citaEntidad, Cita.class);
			cita.setFecha(citaEntidad.getPk().getFecha());
			cita.setBarbero(modelMapper.map(citaEntidad.getPk().getBarbero(), Barbero.class));
			listaCitas.add(cita);
		}
		return listaCitas;
	}

	@Override
	public List<Cita> retornar(String codigoBarbero) {
		// TODO Auto-generated method stub
		return null;
	}

}
