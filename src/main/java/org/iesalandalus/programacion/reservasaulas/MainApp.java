package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;



/*
 * Tarea Online 8, Programación, Ciclo DAM 21-22, IES Al-AndalÃºs, AlmerÃ­a
 * @author Pedro Patricio Cárdenas Figueroa
 * 
 */
public class MainApp {

	public static void main(String[] args) {
		System.out.println("Gestión de reservas de espacios del IES Al-Andalús");
		IFuenteDatos factoriaFicheros= FactoriaFuenteDatos.FICHEROS.crear();
		IModelo modelo= new Modelo(factoriaFicheros);
		IVista vista= new Vista();
		IControlador controlador= new Controlador(modelo, vista);
		
		try {
			controlador.comenzar();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}			
	}

}
