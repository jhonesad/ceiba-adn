package com.ceiba.barberia.infraestructura.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorAgendarCita;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarCitas;

@RestController
@RequestMapping(value = "/api/cita")
public class CitaControlador {

	private final ManejadorListarCitas manejadorListarCitas;
	private final ManejadorAgendarCita manejadorAgendarCita;
	
	public CitaControlador(ManejadorListarCitas manejadorListarCitas, ManejadorAgendarCita manejadorAgendarCita) {
		this.manejadorListarCitas = manejadorListarCitas;
		this.manejadorAgendarCita = manejadorAgendarCita;
	}
	
	@PostMapping("/agendar")
	public ComandoCita agendar(@Valid @RequestBody ComandoCita cita) {
		cita.setId(null);
		return this.manejadorAgendarCita.ejecutar(cita);
	}
	
	@GetMapping("/listar")
	public List<ComandoCita> listar() {
		return this.manejadorListarCitas.ejecutar(); 
	}
}
