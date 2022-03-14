package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Edición clase abstracta
public abstract class Permanencia implements Serializable {
	
	private LocalDate dia;
	
	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//Constructor con parametros
	public Permanencia(LocalDate dia) {
		setDia(dia);	
	}
	
	//Constructor copia
	public Permanencia(Permanencia permanencia) {
		if (permanencia== null) {
			throw new NullPointerException("ERROR: No se puede copiar una permanencia nula.");		
		}
		setDia(permanencia.getDia());
	}

	public LocalDate getDia() {
		return dia;
	}

	private void setDia(LocalDate dia) {
		if (dia== null) {
			throw new NullPointerException("ERROR: El dÃ­a de una permanencia no puede ser nulo.");
			
		}
		
		this.dia = dia;
	}
	public abstract int getPuntos();
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);
	
	
	@Override
	public String toString() {
		return "dia=" + getDia().format(FORMATO_DIA);
	}
	

}
