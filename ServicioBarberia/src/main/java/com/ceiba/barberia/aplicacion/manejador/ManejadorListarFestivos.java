package com.ceiba.barberia.aplicacion.manejador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.fabrica.FabricaNovedad;
import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.servicio.ServicioNovedad;

@Component
public class ManejadorListarFestivos {

	private final ServicioNovedad servicioNovedad;
	private final FabricaNovedad fabricaNovedad;
	
	public ManejadorListarFestivos(ServicioNovedad servicioNovedad, FabricaNovedad fabricaNovedad) {
		this.servicioNovedad = servicioNovedad;
		this.fabricaNovedad = fabricaNovedad;
	}
	
	public List<ComandoNovedad> ejecutar(Date fechaMinima) {
		List<Novedad> festivos = servicioNovedad.listarFestivos(fechaMinima);		
		List<ComandoNovedad> listaFestivos = new ArrayList<>();
		for(Novedad festivo : festivos) {
			listaFestivos.add(fabricaNovedad.novedad(festivo));
		}
		
		return listaFestivos;
	}
}
