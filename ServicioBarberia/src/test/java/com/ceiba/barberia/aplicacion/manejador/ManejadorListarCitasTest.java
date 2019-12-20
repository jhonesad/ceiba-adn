package com.ceiba.barberia.aplicacion.manejador;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.ceiba.barberia.aplicacion.comando.ComandoCita;
import com.ceiba.barberia.aplicacion.fabrica.FabricaCita;
import com.ceiba.barberia.dominio.entidades.Cita;
import com.ceiba.barberia.dominio.servicio.ServicioCita;

public class ManejadorListarCitasTest {

	private ServicioCita servicioCita;
	private FabricaCita fabricaCita;
	private ManejadorListarCitas manejadorListarCitas;
	
	@Before
	public void setUp() {
		servicioCita = Mockito.mock(ServicioCita.class);
		fabricaCita = Mockito.mock(FabricaCita.class);
		manejadorListarCitas = new ManejadorListarCitas(servicioCita, fabricaCita);
	}
	
	@Test
	public void listarCitas() {
		Cita citaMock = Cita.builder().build();
		List<Cita> listaCitasMock = new ArrayList<Cita>();
		listaCitasMock.add(citaMock);
		Mockito.when(servicioCita.listar()).thenReturn(listaCitasMock);
		
		List<ComandoCita> citas = manejadorListarCitas.ejecutar();
		
		assertFalse(citas.isEmpty());
	}
}
