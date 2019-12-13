package com.ceiba.barberia.aplicacion.manejador;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
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
	public ComandoBarbero crear(ComandoBarbero comandoBarbero) {
		Barbero barbero = fabricaBarbero.crear(comandoBarbero);
		barbero = servicioBarbero.crear(barbero);
		comandoBarbero.setId(barbero.getId());
		return comandoBarbero;
	}
	
	public List<ComandoBarbero> listar() {
		List<Barbero> barberos = servicioBarbero.listar();
		List<ComandoBarbero> listaBarberos = new ArrayList<>();
		for(Barbero barbero : barberos) {
			listaBarberos.add(fabricaBarbero.barbero(barbero));
		}
		
		return listaBarberos;
	}
}
