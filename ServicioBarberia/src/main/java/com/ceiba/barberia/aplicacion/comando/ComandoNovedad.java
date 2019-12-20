package com.ceiba.barberia.aplicacion.comando;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(scope = ComandoNovedad.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComandoNovedad {
	
	private Long id;
	@NotNull private Date fechaInicio;
	@NotNull private Date fechaFin;
	private ComandoBarbero barbero;
	@NotNull private String descripcion;
	@NotNull private Boolean festivo;
}
