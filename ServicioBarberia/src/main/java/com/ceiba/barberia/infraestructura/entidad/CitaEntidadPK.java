package com.ceiba.barberia.infraestructura.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class CitaEntidadPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "FECHA")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_BARBERO")
	private BarberoEntidad barbero;
	
	public CitaEntidadPK() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BarberoEntidad getBarbero() {
		return barbero;
	}

	public void setBarbero(BarberoEntidad barbero) {
		this.barbero = barbero;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CitaEntidadPK)) return false;
        CitaEntidadPK that = (CitaEntidadPK) o;
        return Objects.equals(getFecha(), that.getFecha()) &&
                Objects.equals(getBarbero().getCodigo(), that.getBarbero().getCodigo());
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(getFecha(), getBarbero().getCodigo());
    }
}
