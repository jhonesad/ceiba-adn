package com.ceiba.barberia.aplicacion.manejador;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

@Component
public class ManejadorNovedades {

	private final ServicioNovedad servicioNovedad;
	private final FabricaNovedad fabricaNovedad;
	
	public ManejadorNovedades(ServicioNovedad servicioNovedad, FabricaNovedad fabricaNovedad) {
		this.servicioNovedad = servicioNovedad;
		this.fabricaNovedad = fabricaNovedad;
	}
	
	@Transactional
	public ComandoNovedad crear(ComandoNovedad comandoNovedad) {
		Novedad novedad = fabricaNovedad.crear(comandoNovedad);
		novedad = servicioNovedad.crear(novedad);
		comandoNovedad.setId(novedad.getId());
		return comandoNovedad;
	}
	
	public List<ComandoNovedad> listar() {
		List<Novedad> novedades = servicioNovedad.listar();
		List<ComandoNovedad> listaNovedades = new ArrayList<ComandoNovedad>();
		for(Novedad novedad : novedades) {
			listaNovedades.add(fabricaNovedad.novedad(novedad));
		}
		
		return listaNovedades;
	}
}
