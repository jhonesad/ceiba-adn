package com.ceiba.barberia.infraestructura.adaptador;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.infraestructura.entidad.CitaEntidad;

@Repository
public interface CitaRepositorioJPA extends JpaRepository<CitaEntidad, Serializable> {

}
