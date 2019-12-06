package com.ceiba.barberia.infraestructura.controlador;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.manejador.ManejadorCitas;
import com.ceiba.barberia.dominio.entidades.Barbero;
import com.ceiba.barberia.dominio.entidades.Cita;

@RestController
@RequestMapping(value = "/barberia")
public class CitaControlador {

	private final ManejadorCitas manejadorCitas;
	
	public CitaControlador(ManejadorCitas manejadorCitas) {
		this.manejadorCitas = manejadorCitas;
	}
	
	@PostMapping("/agendar-cita")
	public Cita agendarCita() {
		return this.manejadorCitas.agendarCita(new Date(), new Barbero("001", null), true, true, false, "prueba Jhones");
	}
	
	@GetMapping("/listar-citas")
	public List<Cita> listarCitas() {
		return this.manejadorCitas.listarCitas(); 
	}
}
