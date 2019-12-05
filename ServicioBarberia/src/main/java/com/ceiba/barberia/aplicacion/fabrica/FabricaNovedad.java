package com.ceiba.barberia.aplicacion.fabrica;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Novedad;

@Component
public class FabricaNovedad {
	
	public Novedad novedad(Date fechaInicio, Date fechaFin, Barbero barbero, String descripcion, Boolean festivo) {
		return new Novedad(fechaInicio, fechaFin, barbero, descripcion, festivo);
	}
}
