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
@JsonIdentityInfo(scope = ComandoCita.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ComandoCita {
	
	private Long id;
	@NotNull private Date fecha;
	@NotNull private ComandoBarbero barbero;
	@NotNull private Boolean corteCabello;
	@NotNull private Boolean corteBarba;
	@NotNull private Boolean lavado;
	@NotNull private String nombreCliente;	
}
