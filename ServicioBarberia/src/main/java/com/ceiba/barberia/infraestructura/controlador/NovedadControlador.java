package com.ceiba.barberia.infraestructura.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.barberia.aplicacion.comando.ComandoNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorNovedades;

@RestController
@RequestMapping(value = "/barberia")
public class NovedadControlador {
	
	protected static final String ERROR_LISTAR_FESTIVOS_PARSE_FECHA = "No fue posible consultar el calendario de dias festivos a partir de la fecha";
	
	private final ManejadorNovedades manejadorNovedades;
	
	public NovedadControlador(ManejadorNovedades manejadorNovedades) {
		this.manejadorNovedades = manejadorNovedades;
	}
	
	@PostMapping("/crear-novedad")
	public ComandoNovedad crearNovedad(@Valid @RequestBody ComandoNovedad novedad) {	
		novedad.setId(null);
		validateAndSetBarberoNuevaNovedad(novedad);
		
		return manejadorNovedades.crear(novedad);
	}
	
	@GetMapping("/listar-novedades")
	public List<ComandoNovedad> listarNovedades() {
		return this.manejadorNovedades.listar(); 
	}
	
	@GetMapping("/listar-festivos/{FECHA_MINIMA}")
	public List<ComandoNovedad> listarFestivos(@Valid @PathVariable(name = "FECHA_MINIMA") String strFechaMinima) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date fechaMinima = formatter.parse(strFechaMinima);
			return this.manejadorNovedades.listarFestivos(fechaMinima);
		} catch(ParseException ex) {
			throw new RuntimeException(ERROR_LISTAR_FESTIVOS_PARSE_FECHA);
		}
		 
	}
	
	protected void validateAndSetBarberoNuevaNovedad(ComandoNovedad novedad) {
		if(novedad.getBarbero() != null && (novedad.getBarbero().getId() == null || novedad.getBarbero().getId() == 0l)) {
			novedad.setBarbero(null);
		}
	}
}
