package com.ceiba.barberia.aplicacion.manejador;

import java.util.ArrayList;
import java.util.Date;
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
		return construirListaNovedades(novedades);
	}
	
	public List<ComandoNovedad> listarFestivos(Date fechaMinima) {
		List<Novedad> novedades = servicioNovedad.listarFestivos(fechaMinima);		
		return construirListaNovedades(novedades);
	}
	
	protected List<ComandoNovedad> construirListaNovedades(List<Novedad> novedades) {
		List<ComandoNovedad> listaNovedades = new ArrayList<>();
		for(Novedad novedad : novedades) {
			listaNovedades.add(fabricaNovedad.novedad(novedad));
		}
		
		return listaNovedades;
	}
}
