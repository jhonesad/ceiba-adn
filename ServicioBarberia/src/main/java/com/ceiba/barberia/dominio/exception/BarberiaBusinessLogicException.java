package com.ceiba.barberia.dominio.exception;

public class BarberiaBusinessLogicException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public BarberiaBusinessLogicException(String errorMessage) {
		super(errorMessage);
	}
	
	public BarberiaBusinessLogicException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
