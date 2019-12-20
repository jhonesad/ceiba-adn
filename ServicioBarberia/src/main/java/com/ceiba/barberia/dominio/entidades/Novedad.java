package com.ceiba.barberia.dominio.entidades;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Novedad {
	
	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private Barbero barbero;
	private String descripcion;
	private Boolean festivo;
}
