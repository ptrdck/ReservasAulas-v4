package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.io.Serializable;
import java.util.Objects;

public class Reserva implements Serializable {
	
	//(0,1)
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;
	
	//Constructor con parámetros
	public Reserva(Profesor profesor, Aula aula, Permanencia permanencia) {
		
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	
	//Constructor copia
	public Reserva(Reserva reserva) {
		if (reserva== null) {
			throw new NullPointerException("ERROR: no se puede copiar una reserva nula");
		}
		setProfesor(reserva.getProfesor());
		setAula(reserva.getAula());
		setPermanencia(reserva.getPermanencia());
	}
	
	//validador profesor
	private void setProfesor(Profesor profesor) {
		if (profesor== null) {
			throw new NullPointerException("ERROR: La reserva debe estar a nombre de un profesor.");
		}
		this.profesor=  new Profesor(profesor);
		
	}
	public Profesor getProfesor() {
		return profesor;
	}
	
	//Validador aula
	private void setAula(Aula aula) {
		if (aula== null) {
			throw new NullPointerException("ERROR: La reserva debe ser para un aula concreta.");
		}
		this.aula= aula;
	}
	public Aula getAula() {
		return aula;
	}
	
	//validador permanencia
	private void setPermanencia(Permanencia permanencia) {
		if (permanencia== null) {
			throw new NullPointerException("ERROR: La reserva se debe hacer para una permanencia concreta.");
			
		}
		// Comprobación de clase a través de operador instanceof
		if (permanencia instanceof PermanenciaPorHora) {
			this.permanencia= new PermanenciaPorHora((PermanenciaPorHora) permanencia);
		} else if (permanencia instanceof PermanenciaPorTramo) {
			this.permanencia= new PermanenciaPorTramo((PermanenciaPorTramo) permanencia);
		}
		this.permanencia= permanencia;
	}
	public Permanencia getPermanencia() {
		return permanencia;
	}
	
	// Método que obtiene un profesor ficticio y una reserva con un aula y una permanencia obtenida como parámetros
	public static Reserva getReservaFicticia(Aula aula, Permanencia permanencia) {
		return new Reserva(Profesor.getProfesorFicticio("ppcf_11@outlook.com"), aula, permanencia);
	}
	
	//Cantidad de puntos entre la suma de Permanencia y aula. 
	public float getPuntos()
	{
		return permanencia.getPuntos()+aula.getPuntos();
	}
	@Override
	public int hashCode() {
		return Objects.hash(aula, permanencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		return Objects.equals(aula, other.aula) && Objects.equals(permanencia, other.permanencia);
	}

	@Override
	public String toString() {
		return "Profesor=" + getProfesor() + ", aula=" + getAula() + ", permanencia= " + getPermanencia() + ", puntos=" + getPuntos();
	}
	
	
}
