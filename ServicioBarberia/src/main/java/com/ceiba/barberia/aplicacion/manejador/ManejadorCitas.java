package com.ceiba.barberia.aplicacion.manejador;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

@Component
public class ManejadorCitas {

	private final ServicioCita servicioCita;
	private final FabricaCita fabricaCita;
	
	public ManejadorCitas(ServicioCita servicioCita, FabricaCita fabricaCita) {
		this.servicioCita = servicioCita;
		this.fabricaCita = fabricaCita;
	}
	
	@Transactional
	public ComandoCita agendarCita(ComandoCita comandoCita) {
		Cita cita =  fabricaCita.crear(comandoCita);
		cita = servicioCita.agendarCita(cita);
		comandoCita.setId(cita.getId());
		return comandoCita;
	}
	
	public List<ComandoCita> listarCitas() {
		List<Cita> citas = servicioCita.listarCitas();
		List<ComandoCita> listaCitas = new ArrayList<ComandoCita>();
		for(Cita cita : citas) {
			listaCitas.add(fabricaCita.cita(cita));
		}
		
		return listaCitas;
	}
}
