package com.ceiba.barberia.infraestructura.adaptador;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.barberia.infraestructura.entidad.BarberoEntidad;

@Repository
public interface BarberoRepositorioJPA extends JpaRepository<BarberoEntidad, Serializable> {

}
