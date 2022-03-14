package org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio;

public enum Tramo {
	MANANA("Mañana"), TARDE("Tarde");
	
	private String mostrarCadena;
	
	private Tramo(String mostrarCadena) {
		this.mostrarCadena= mostrarCadena;
	}

	@Override
	public String toString() {
		return mostrarCadena;
	}
	

}
