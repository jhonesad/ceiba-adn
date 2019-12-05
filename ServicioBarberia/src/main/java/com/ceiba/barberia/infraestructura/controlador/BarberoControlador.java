package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.manejador.ManejadorBarberos;
import com.ceiba.barberia.dominio.entidades.Barbero;

@RestController
@RequestMapping(value = "/barberia")
public class BarberoControlador {
	
	private final ManejadorBarberos manejadorBarberos;
	
	public BarberoControlador(ManejadorBarberos manejadorBarberos) {
		this.manejadorBarberos = manejadorBarberos;
	}
	
	@PostMapping("/crear-barbero")
	public Barbero crearBarbero(@RequestBody Map<String, Object> body) {
		return this.manejadorBarberos.crear(body.get("codigo").toString(), body.get("nombre").toString());
	}
	
	@GetMapping("/listar-barberos")
	public List<Barbero> listarBarberos() {
		return this.manejadorBarberos.listar();
	}
}
