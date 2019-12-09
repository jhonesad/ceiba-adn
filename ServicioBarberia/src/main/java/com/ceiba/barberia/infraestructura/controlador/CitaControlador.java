package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorCitas;

@RestController
@RequestMapping(value = "/barberia")
public class CitaControlador {

	private final ManejadorCitas manejadorCitas;
	
	public CitaControlador(ManejadorCitas manejadorCitas) {
		this.manejadorCitas = manejadorCitas;
	}
	
	@PostMapping("/agendar-cita")
	public ComandoCita agendarCita(@RequestBody ComandoCita cita) {
		cita.setId(null);
		return this.manejadorCitas.agendarCita(cita);
	}
	
	@GetMapping("/listar-citas")
	public List<ComandoCita> listarCitas() {
		return this.manejadorCitas.listarCitas(); 
	}
}
