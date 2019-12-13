package com.ceiba.barberia.dominio.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ceiba.barberia.dominio.entidades.Novedad;
import com.ceiba.barberia.dominio.puerto.repositorio.RepositorioNovedad;

@Service
public class ServicioNovedad {
	
	private RepositorioNovedad repositorioNovedad;
	
	public ServicioNovedad(RepositorioNovedad repositorioNovedad) {
		this.repositorioNovedad = repositorioNovedad;
	}
	
	public Novedad crear(Novedad novedad) {
		//TODO validaciones
		//Si es festivo: 
		//	- que las fechas no sean menores al dia de hoy
		//	- que el festivo no exista ya en la BD
		//Si No es festivo
		//	- que las fechas no sean menores al dia de hoy
		//	- validar horas y minutos mayores al momento
		//	- validar que la fecha y hora de fin sean superiores al inicio
		//	- validar que el barbero ya no tenga novedades registradas en el mismo rango de tiempo
		//	- validar que el barbero ya no tenga citas asignadas en el rango de tiempo
		
		return repositorioNovedad.crear(novedad);
	}
	
	public List<Novedad> listar() {
		return repositorioNovedad.retornar();
	}
	
	public List<Novedad> listarFestivos(Date fechaMinima) {
		return repositorioNovedad.listarFestivos(fechaMinima);
	}
}
