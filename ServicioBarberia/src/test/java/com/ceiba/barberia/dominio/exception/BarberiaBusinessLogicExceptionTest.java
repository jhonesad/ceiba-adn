package com.ceiba.barberia.dominio.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class BarberiaBusinessLogicExceptionTest {

	@Test
	public void crearInstanciaSoloConMensaje() {
		String mensajeError = "mensaje de error";
		
		BarberiaBusinessLogicException exception = new BarberiaBusinessLogicException(mensajeError);
		
		assertNotNull(exception);
		assertEquals(mensajeError, exception.getMessage());
	}
	
	@Test
	public void crearInstanciaConMensajeYTrazaError() {
		String mensajeError = "mensaje de error";
		
		BarberiaBusinessLogicException exception = null;
		String pruebaError = "PRUEBA";
		
		try {
			Long error = Long.valueOf(pruebaError);
			fail("Deberia fallar:" + error);
		} catch(Exception ex) {
			exception = new BarberiaBusinessLogicException(mensajeError, ex);
		}
		
		assertNotNull(exception);
		assertEquals(mensajeError, exception.getMessage());
	}
}
