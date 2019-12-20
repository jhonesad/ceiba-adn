package com.ceiba.barberia.aplicacion.manejador;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.fabrica.FabricaBarbero;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.servicio.ServicioBarbero;

@Component
public class ManejadorCrearBarbero {

	private final ServicioBarbero servicioBarbero;
	private final FabricaBarbero fabricaBarbero;
	
	public ManejadorCrearBarbero(ServicioBarbero servicioBarbero, FabricaBarbero fabricaBarbero) {
		this.servicioBarbero = servicioBarbero;
		this.fabricaBarbero = fabricaBarbero;
	}
	
	@Transactional
	public ComandoBarbero ejecutar(ComandoBarbero comandoBarbero) {
		Barbero barbero = fabricaBarbero.crear(comandoBarbero);
		barbero = servicioBarbero.crear(barbero);
		comandoBarbero.setId(barbero.getId());
		return comandoBarbero;
	}
}
