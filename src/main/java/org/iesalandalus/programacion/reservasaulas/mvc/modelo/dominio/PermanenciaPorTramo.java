package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

//herencia de atributos y métodos desde Permanencia
public class PermanenciaPorTramo extends Permanencia {
	
	// Por cada permanencia por tramo se quitarán 10 puntos
	private static final int PUNTOS= 10;
	
	private Tramo tramo;
	
	public PermanenciaPorTramo(LocalDate dia, Tramo tramo) {
		super(dia);
		setTramo(tramo);
	}
	
	public PermanenciaPorTramo(PermanenciaPorTramo permanenciaPorTramo) {
		super(permanenciaPorTramo);
		setTramo(permanenciaPorTramo.getTramo());
	}
	public Tramo getTramo() {
		return tramo;
	}
	
	private void setTramo(Tramo tramo) {
		if (tramo== null) {
			throw new NullPointerException("ERROR: El tramo de una permanencia no puede ser nulo.");
		}
		this.tramo= tramo;
	}
	
	@Override
	public int getPuntos() {
		return PUNTOS;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getDia(), tramo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermanenciaPorTramo other = (PermanenciaPorTramo) obj;
		return Objects.equals(getDia(), other.getDia()) && tramo== other.tramo;
	}

	@Override
	public String toString() {
		return super.toString() + ", tramo=" + getTramo();
	}
	
	
	
	
	
	
	

}
