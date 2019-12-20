package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorCrearBarbero;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarBarberos;

@RestController
@RequestMapping(value = "/api/barbero")
public class BarberoControlador {
	
	private final ManejadorListarBarberos manejadorListarBarberos;
	private final ManejadorCrearBarbero manejadorCrearBarbero;
	
	public BarberoControlador(ManejadorListarBarberos manejadorListarBarberos, ManejadorCrearBarbero manejadorCrearBarbero) {
		this.manejadorListarBarberos = manejadorListarBarberos;
		this.manejadorCrearBarbero = manejadorCrearBarbero;
	}
	
	@PostMapping("/crear")
	public ComandoBarbero crear(@Valid @RequestBody ComandoBarbero barbero) {
		barbero.setId(null);
		return this.manejadorCrearBarbero.ejecutar(barbero);
	}
	
	@GetMapping("/listar")
	public List<ComandoBarbero> listar() {
		return this.manejadorListarBarberos.ejecutar();
	}
}
