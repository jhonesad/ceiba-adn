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
import com.ceiba.barberia.aplicacion.manejador.ManejadorCrearNovedad;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarFestivos;
import com.ceiba.barberia.aplicacion.manejador.ManejadorListarNovedades;
import com.ceiba.barberia.infraestructura.controlador.exception.BarberiaDataValidationException;

@RestController
@RequestMapping(value = "/api/novedad")
public class NovedadControlador {
	
	protected static final String ERROR_LISTAR_FESTIVOS_PARSE_FECHA = "No fue posible consultar el calendario de dias festivos a partir de la fecha";
	
	private final ManejadorCrearNovedad manejadorCrearNovedad;
	private final ManejadorListarNovedades manejadorListarNovedades;
	private final ManejadorListarFestivos manejadorListarFestivos;
	
	public NovedadControlador(ManejadorCrearNovedad manejadorCrearNovedad, 
			ManejadorListarNovedades manejadorListarNovedades, ManejadorListarFestivos manejadorListarFestivos) {
		
		this.manejadorCrearNovedad = manejadorCrearNovedad;
		this.manejadorListarNovedades = manejadorListarNovedades;
		this.manejadorListarFestivos = manejadorListarFestivos;
	}
	
	@PostMapping("/crear")
	public ComandoNovedad crear(@Valid @RequestBody ComandoNovedad novedad) {	
		novedad.setId(null);
		validateAndSetBarberoNuevaNovedad(novedad);
		
		return manejadorCrearNovedad.ejecutar(novedad);
	}
	
	@GetMapping("/listar")
	public List<ComandoNovedad> listar() {
		return this.manejadorListarNovedades.ejecutar(); 
	}
	
	@GetMapping("/listar-festivos/{FECHA_MINIMA}")
	public List<ComandoNovedad> listarFestivos(@Valid @PathVariable(name = "FECHA_MINIMA") String strFechaMinima) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			Date fechaMinima = formatter.parse(strFechaMinima);
			return this.manejadorListarFestivos.ejecutar(fechaMinima);
		} catch(ParseException ex) {
			throw new BarberiaDataValidationException(ERROR_LISTAR_FESTIVOS_PARSE_FECHA);
		}
		 
	}
	
	protected void validateAndSetBarberoNuevaNovedad(ComandoNovedad novedad) {
		if(novedad.getBarbero() != null && (novedad.getBarbero().getId() == null || novedad.getBarbero().getId() == 0l)) {
			novedad.setBarbero(null);
		}
	}
}
