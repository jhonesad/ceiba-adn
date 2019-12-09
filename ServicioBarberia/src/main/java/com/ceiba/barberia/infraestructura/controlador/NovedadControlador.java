package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorNovedades;

@RestController
@RequestMapping(value = "/barberia")
public class NovedadControlador {
	
	private final ManejadorNovedades manejadorNovedades;
	
	public NovedadControlador(ManejadorNovedades manejadorNovedades) {
		this.manejadorNovedades = manejadorNovedades;
	}
	
	@PostMapping("/crear-novedad")
	public ComandoNovedad crearNovedad(@Valid @RequestBody ComandoNovedad novedad) {	
		novedad.setId(null);
		return manejadorNovedades.crear(novedad);
	}
	
	@GetMapping("/listar-novedades")
	public List<ComandoNovedad> listarNovedades() {
		return this.manejadorNovedades.listar(); 
	}
}
