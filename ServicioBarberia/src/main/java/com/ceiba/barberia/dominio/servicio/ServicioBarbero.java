package com.ceiba.barberia.dominio.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.exception.BarberiaBusinessLogicException;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioBarbero;

@Service
public class ServicioBarbero {
	
	protected static final String ERROR_BARBERO_GUARDADO = "El barbero ya se encuentra registrado";
	
	private RepositorioBarbero repositorioBarbero;
	
	public ServicioBarbero(RepositorioBarbero repositorioBarbero) {
		this.repositorioBarbero = repositorioBarbero;
	}
	
	public Barbero crear(Barbero barbero) {
		if(nombreBarberoExisteEnBD(barbero.getNombre())) {
			throw new BarberiaBusinessLogicException(ERROR_BARBERO_GUARDADO);
		}
		
		return this.repositorioBarbero.crear(barbero);
	}
	
	public List<Barbero> listar() {
		return repositorioBarbero.listar();
	}
	
	protected boolean nombreBarberoExisteEnBD(String nombre) {
		List<Barbero> listaBarberos = repositorioBarbero.listarPorNombre(nombre);
		return listaBarberos != null;
	}
}
