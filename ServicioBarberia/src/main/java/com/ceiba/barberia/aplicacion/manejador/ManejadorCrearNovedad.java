package com.ceiba.barberia.aplicacion.manejador;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

@Component
public class ManejadorCrearNovedad {

	private final ServicioNovedad servicioNovedad;
	private final FabricaNovedad fabricaNovedad;
	
	public ManejadorCrearNovedad(ServicioNovedad servicioNovedad, FabricaNovedad fabricaNovedad) {
		this.servicioNovedad = servicioNovedad;
		this.fabricaNovedad = fabricaNovedad;
	}
	
	@Transactional
	public ComandoNovedad ejecutar(ComandoNovedad comandoNovedad) {
		Novedad novedad = fabricaNovedad.crear(comandoNovedad);
		novedad = servicioNovedad.crear(novedad);
		comandoNovedad.setId(novedad.getId());
		return comandoNovedad;
	}
}
