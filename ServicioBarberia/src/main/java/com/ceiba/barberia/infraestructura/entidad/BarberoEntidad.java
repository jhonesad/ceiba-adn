package com.ceiba.barberia.infraestructura.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BARBERO")
public class BarberoEntidad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuencia_barbero")
	@SequenceGenerator(name="secuencia_barbero", sequenceName = "BARBERO_SECUENCIA", initialValue=1, allocationSize=100)
	@Column(name = "ID", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false)
	private String nombre;
	
	public BarberoEntidad() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
