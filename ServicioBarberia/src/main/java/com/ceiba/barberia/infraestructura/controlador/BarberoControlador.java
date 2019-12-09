package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorBarberos;

@RestController
@RequestMapping(value = "/barberia")
public class BarberoControlador {
	
	private final ManejadorBarberos manejadorBarberos;
	
	public BarberoControlador(ManejadorBarberos manejadorBarberos) {
		this.manejadorBarberos = manejadorBarberos;
	}
	
	@PostMapping("/crear-barbero")
	public ComandoBarbero crearBarbero(@RequestBody ComandoBarbero barbero) {
		barbero.setId(null);
		return this.manejadorBarberos.crear(barbero);
	}
	
	@GetMapping("/listar-barberos")
	public List<ComandoBarbero> listarBarberos() {
		return this.manejadorBarberos.listar();
	}
}
