package com.ceiba.barberia.infraestructura.controlador.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class BarberiaDataValidationExceptionTest {

	@Test
	public void crearInstanciaSoloConMensaje() {
		String mensajeError = "mensaje de error";
		
		BarberiaDataValidationException exception = new BarberiaDataValidationException(mensajeError);
		
		assertNotNull(exception);
		assertEquals(mensajeError, exception.getMessage());
	}
	
	@Test
	public void crearInstanciaConMensajeYTrazaError() {
		String mensajeError = "mensaje de error";
		
		BarberiaDataValidationException exception = null;
		String pruebaError = "PRUEBA";
		
		try {
			Long error = Long.valueOf(pruebaError);
			fail("Deberia fallar:" + error);
		} catch(Exception ex) {
			exception = new BarberiaDataValidationException(mensajeError, ex);
		}
		
		assertNotNull(exception);
		assertEquals(mensajeError, exception.getMessage());
	}
}
