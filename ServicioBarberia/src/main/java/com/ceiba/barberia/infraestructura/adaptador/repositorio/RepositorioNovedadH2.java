package com.ceiba.barberia.infraestructura.adaptador.repositorio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;
import com.ceiba.barberia.infraestructura.adaptador.NovedadRepositorioJPA;
import com.ceiba.barberia.infraestructura.entidad.NovedadEntidad;

@Repository
public class RepositorioNovedadH2 implements RepositorioNovedad {

	@Autowired
	private EntityManager entityManager;
	
	private final NovedadRepositorioJPA novedadRepositorioJPA;
	private ModelMapper modelMapper = new ModelMapper();
	
	public RepositorioNovedadH2(NovedadRepositorioJPA novedadRepositorioJPA) {
		this.novedadRepositorioJPA = novedadRepositorioJPA;
	}
	
	@Override
	public Novedad crear(Novedad novedad) {
		NovedadEntidad novedadEntidad = modelMapper.map(novedad, NovedadEntidad.class);
		novedadRepositorioJPA.save(novedadEntidad);
		novedad.setId(novedadEntidad.getId());
		return novedad;
	}

	@Override
	public List<Novedad> retornar() {
		List<NovedadEntidad> listaNovedadEntidad = novedadRepositorioJPA.findAll();
		return retornarListaNovedades(listaNovedadEntidad);
	}
	
	@Override
	public List<Novedad> listarPorBarbero(Long idBarbero) {
		TypedQuery<NovedadEntidad> query = getEntityManager()
				.createQuery("SELECT n FROM NovedadEntidad n WHERE n.barbero IS NOT NULL AND n.barbero.id = :idBarbero", NovedadEntidad.class);
		query.setParameter("idBarbero", idBarbero);
		List<NovedadEntidad> listaNovedadEntidad = query.getResultList();
		
		return retornarListaNovedades(listaNovedadEntidad);
	}
	
	private List<Novedad> retornarListaNovedades(List<NovedadEntidad> listaNovedadEntidad) {
		List<Novedad> listaNovedades = new ArrayList<>();
		for(NovedadEntidad novedadEntidad : listaNovedadEntidad) {
			listaNovedades.add(modelMapper.map(novedadEntidad, Novedad.class));
		}
		return listaNovedades;
	}

	@Override
	public List<Novedad> listarFestivos(Date fechaMinima) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaMinima);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		
		TypedQuery<NovedadEntidad> query = getEntityManager()
				.createQuery("SELECT n FROM NovedadEntidad n "
						+ "WHERE n.festivo = TRUE "
						+ "AND n.barbero IS NULL "
						+ "AND n.fechaInicio >= :fechaMinima", NovedadEntidad.class);
		
		query.setParameter("fechaMinima", calendar.getTime(), TemporalType.DATE);
		List<NovedadEntidad> listaNovedadEntidad = query.getResultList();
		
		return retornarListaNovedades(listaNovedadEntidad);
	}
	
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
}
