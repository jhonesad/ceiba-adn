package com.ceiba.barberia.aplicacion.manejador;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

@Component
public class ManejadorListarCitas {

	private final ServicioCita servicioCita;
	private final FabricaCita fabricaCita;
	
	public ManejadorListarCitas(ServicioCita servicioCita, FabricaCita fabricaCita) {
		this.servicioCita = servicioCita;
		this.fabricaCita = fabricaCita;
	}
	
	public List<ComandoCita> ejecutar() {
		List<Cita> citas = servicioCita.listar();
		List<ComandoCita> listaCitas = new ArrayList<>();
		for(Cita cita : citas) {
			listaCitas.add(fabricaCita.cita(cita));
		}
		
		return listaCitas;
	}
}
