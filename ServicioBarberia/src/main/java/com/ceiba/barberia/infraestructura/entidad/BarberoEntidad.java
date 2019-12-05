package com.ceiba.barberia.infraestructura.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BARBERO")
public class BarberoEntidad {
	
	@Id
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOMBRE", nullable = false)
	private String nombre;
	
	public BarberoEntidad() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
