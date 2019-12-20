package com.ceiba.barberia.aplicacion.manejador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

@Component
public class ManejadorListarNovedades {

	private final ServicioNovedad servicioNovedad;
	private final FabricaNovedad fabricaNovedad;
	
	public ManejadorListarNovedades(ServicioNovedad servicioNovedad, FabricaNovedad fabricaNovedad) {
		this.servicioNovedad = servicioNovedad;
		this.fabricaNovedad = fabricaNovedad;
	}
	
	public List<ComandoNovedad> ejecutar() {
		List<Novedad> novedades = servicioNovedad.listar();
		List<ComandoNovedad> listaNovedades = new ArrayList<>();
		for(Novedad novedad : novedades) {
			listaNovedades.add(fabricaNovedad.novedad(novedad));
		}
		
		return listaNovedades;
	}
}
