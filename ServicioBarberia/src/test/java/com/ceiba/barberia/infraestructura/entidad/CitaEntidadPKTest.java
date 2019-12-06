package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Test;

public class CitaEntidadPKTest {

	@Test
	public void validadGettersSetters() {
		Date fecha = new Date();
		BarberoEntidad barbero = new BarberoEntidad();
		barbero.setCodigo("555");
		barbero.setNombre("nombre");
		
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		citaEntidadPK.setFecha(fecha);
		citaEntidadPK.setBarbero(barbero);
		
		assertEquals(fecha, citaEntidadPK.getFecha());
		assertEquals(barbero, citaEntidadPK.getBarbero());
	}
}
