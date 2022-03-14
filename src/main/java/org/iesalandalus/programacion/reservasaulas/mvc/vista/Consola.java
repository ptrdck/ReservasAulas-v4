package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	
	private static final DateTimeFormatter FORMATO_DIA= DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//Constructor
	private Consola() {
		
	}
	
	public static void mostrarMenu() {
		//ciclo for each para recorrer arrays
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String formatoCad= "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(formatoCad, 0).replace("0", "-"));
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.println("\nSeleccione una opción: ");
			ordinalOpcion= Entrada.entero();
		}while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}
	
	public static Aula leerAula() {
		System.out.println("Introduzca el nombre del aula: ");
		String nombre= Entrada.cadena();
		System.out.print("Introduce el n�mero de puestos del aula");
		int puestos= Entrada.entero();
		return new Aula(nombre, puestos);
	}
	
	public static int leerNumerosDePuestos() {
		System.out.print("Introduce la cantidad de puestos del aula: ");
		
		int puestos= Entrada.entero();
		return puestos;
	}
	
	public static Aula leerAulaFicticia() {
		System.out.print("Introduce el nombre del aula: ");
		String nombre= Entrada.cadena();
		return Aula.getAulaFicticia(nombre);
	}
	
	public static String leerNombreAula() {
		System.out.println("Introduzca el nombre del aula: ");
		String nombreAula= Entrada.cadena();
		return nombreAula;
	}
	
	
	public static Profesor leerProfesor() {
		System.out.println("Introduzca el nombre del profesor: ");
		String nombre= Entrada.cadena();
		System.out.println("Introduzca el correo del profesor: ");
		String correo= Entrada.cadena();
		System.out.println("Introduzca el télefono del profesor: ");
		String telefono= Entrada.cadena();
		Profesor profesor= (telefono== null || telefono.trim().equals(""))
				? new Profesor(nombre, correo)
						: new Profesor(nombre, correo, telefono);
		return profesor;
	}
	
	public static String leerNombreProfesor() {
		System.out.println("Introduzca el nombre del profesor");
		String nombreProfesor= Entrada.cadena();
		return nombreProfesor;
	}
	
	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduce el correo del profesor: ");
		return Profesor.getProfesorFicticio(Entrada.cadena());
	}
	
	public static Tramo leerTramo() {
		System.out.println("Introduzca el tramo en que desea reservar (0. Mañana, 1. Tarde): ");
		int tramoReserva= Entrada.entero();
		Tramo tramo = null;
		if (tramoReserva< 0 || tramoReserva>= Tramo.values().length) {
			System.out.println("ERROR: La opción seleccionada no pertenece a ningún tramo.");
		} else {
			tramo= Tramo.values()[tramoReserva];
		}
		return tramo;
	}
	
	public static LocalDate leerDia() {
		LocalDate dia= null;
		System.out.println("Introduzca una fecha con el siguiente formato (dd/MM/yyyy)");
		String fechaCad= Entrada.cadena();
		try {
			dia= LocalDate.parse(fechaCad, FORMATO_DIA);
		} catch (DateTimeParseException e) {
			System.out.println("ERROR: El formato de fecha incorrecto. Formato fecha (dd/MM/yyyy)");
		}
		return dia;
	}
	
	public static Permanencia leerPermanencia() {
		int ordinalPermanencia= Consola.elegirPermanencia();
		LocalDate dia= leerDia();
		Permanencia permanencia= null;
		if (ordinalPermanencia== 0){
			Tramo tramo= leerTramo();
			permanencia= new PermanenciaPorTramo(dia, tramo);
		}
		else if (ordinalPermanencia== 1) {
			LocalTime hora= leerHora();
			permanencia= new PermanenciaPorHora(dia, hora);
		}
		return permanencia;
	}
	
	public static int elegirPermanencia() {
		int ordinalPermanencia;
		do {
			System.out.print("\nElige una permanencia(0. Por Tramo, 1. Por Hora): ");
			ordinalPermanencia= Entrada.entero();
		} while (ordinalPermanencia< 0 || ordinalPermanencia> 1);
		return ordinalPermanencia;
	}
	
	public static LocalTime leerHora() {
		LocalTime hora= null;
		String formato= "HH:mm";
		DateTimeFormatter patron= DateTimeFormatter.ofPattern(formato);
		System.out.printf("Introduce la hora (%s): ", formato);
		String horaForm= Entrada.cadena();
		
		try {
			hora= LocalTime.parse(horaForm, patron);
		} catch (DateTimeParseException e) {
			System.out.print("ERROR: El formato de la hora no es v�lido. ");
		}
		return hora;
	}
	
	
	public static Reserva leerReserva() {
		return new Reserva(leerProfesor(), leerAula(), leerPermanencia());
	}
	
	public static Reserva leerReservaFicticia() {
		return Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());
	}
}
