package com.ceiba.barberia.infraestructura.controlador.exception;

public class BarberiaDataValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BarberiaDataValidationException(String errorMessage) {
		super(errorMessage);
	}
	
	public BarberiaDataValidationException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
