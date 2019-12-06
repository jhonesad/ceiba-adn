package com.ceiba.barberia.infraestructura.entidad;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Test;

import com.ceiba.barberia.dominio.entidades.Cita;

public class CitaEntidadPKTest {

	@Test
	public void validateGettersSetters() {
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
	
	@Test
	public void validateEqualsWhenSameObject() {
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		
		Boolean validacion = citaEntidadPK.equals(citaEntidadPK);
		
		assertTrue(validacion);
	}
	
	@Test
	public void validateEqualsWhenDifferentInstance() {
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		Cita cita = new Cita();
		
		Boolean validacion = citaEntidadPK.equals(cita);
		
		assertFalse(validacion);
	}
	
	@Test
	public void validateEqualsWhenDifferentArguments() {
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		citaEntidadPK.setFecha(new Date());
		citaEntidadPK.setBarbero(new BarberoEntidad());
		CitaEntidadPK citaEntidadPK2 = new CitaEntidadPK();
		
		Boolean validacion = citaEntidadPK.equals(citaEntidadPK2);
		
		assertFalse(validacion);
	}
	
	@Test
	public void validateEqualsWhenSameDateDifferentBarbero() {
		Date fecha = new Date();
		BarberoEntidad barbero = new BarberoEntidad();
		barbero.setCodigo("001");
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		citaEntidadPK.setFecha(fecha);
		citaEntidadPK.setBarbero(barbero);
		CitaEntidadPK citaEntidadPK2 = new CitaEntidadPK();
		citaEntidadPK2.setFecha(fecha);
		citaEntidadPK2.setBarbero(new BarberoEntidad());
		
		Boolean validacion = citaEntidadPK.equals(citaEntidadPK2);
		
		assertFalse(validacion);
	}
	
	@Test
	public void validateEqualsWhenSameFields() {
		Date fecha = new Date();
		BarberoEntidad barbero1 = new BarberoEntidad();
		barbero1.setCodigo("001");
		BarberoEntidad barbero2 = new BarberoEntidad();
		barbero2.setCodigo("001");
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		citaEntidadPK.setFecha(fecha);
		citaEntidadPK.setBarbero(barbero1);
		CitaEntidadPK citaEntidadPK2 = new CitaEntidadPK();
		citaEntidadPK2.setFecha(fecha);
		citaEntidadPK2.setBarbero(barbero2);
		
		Boolean validacion = citaEntidadPK.equals(citaEntidadPK2);
		
		assertTrue(validacion);
	}
	
	@Test
	public void validateHashCode() {
		Date fecha = new Date();
		BarberoEntidad barbero = new BarberoEntidad();
		barbero.setCodigo("001");
		CitaEntidadPK citaEntidadPK = new CitaEntidadPK();
		citaEntidadPK.setFecha(fecha);
		citaEntidadPK.setBarbero(barbero);
		
		Integer hashCode = citaEntidadPK.hashCode();
		
		assertNotNull(hashCode);
	}
}
