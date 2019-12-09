package com.ceiba.barberia.infraestructura.controlador;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ControladorText {

	MockMvc mockMvc;
	
	protected String asJsonString(Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
