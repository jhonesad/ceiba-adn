package com.ceiba.barberia.aplicacion.manejador;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;

@Component
public class ManejadorBarberos {

	private final ServicioBarbero servicioBarbero;
	private final FabricaBarbero fabricaBarbero;
	
	public ManejadorBarberos(ServicioBarbero servicioBarbero, FabricaBarbero fabricaBarbero) {
		this.servicioBarbero = servicioBarbero;
		this.fabricaBarbero = fabricaBarbero;
	}
	
	@Transactional
	public Barbero crear(String codigo, String nombre) {
		Barbero barbero = fabricaBarbero.barbero(codigo, nombre);
		return servicioBarbero.crear(barbero);
	}
	
	public List<Barbero> listar() {
		return servicioBarbero.listar();
	}
}
