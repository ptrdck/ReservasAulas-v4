package org.iesalandalus.programacion.reservasaulas.mvc.vista;

import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;

public class Vista implements IVista {
	
	// incicialización de parámetros de validación de entradas
	private static final String ERROR= "ERROR: ";
	private static final String NOMBRE_VALIDO= "Nombre v�lido";
	private static final String CORREO_VALIDO= "Correo v�lido";
	private IControlador controlador;
	
	//Constructor
	public Vista() {
		Opcion.setVista(this);
	}
	//Consulta a controlador
	@Override
	public void setControlador(IControlador controlador) {
		this.controlador= controlador;
	}	
	
	//Invoca la opcion comenzar de Opcion
	@Override
	public void comenzar()
	{
		int ordinalOpcion;
		do 
		{
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}
	//método para salir de Opcion
	@Override
	public void salir() {
		controlador.terminar();
	}
	//Invoca método insertar de Aula
	@Override
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		
		try {
			controlador.insertarAula(Consola.leerAula());
			System.out.println("Aula insertada con éxito.");
			
			//iniciar un catch para capturar las excepciones de la clase aula
			// así también como para el método insertar
		} catch (NullPointerException | OperationNotSupportedException | IllegalArgumentException e){
			System.out.println(e.getMessage());
		}
	
	}
	//opcion que invoca al méotodo borrar de Aula
	@Override
	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		try {
			controlador.borrarAula(Consola.leerAula());
			System.out.println("Aula borrada con éxito.");
			
			//iniciar un catch para capturar excepciones de la clase Aula como del método borrar
		}catch (NullPointerException | OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	//invoca opción buscar de aulas
	@Override
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula;
		
		try {
			aula= controlador.buscarAula(Consola.leerAula());
			String mensaje= (aula!= null) ? aula.toString(): "El aula indicada no se encuentra en el sistema";
			System.out.println(mensaje);
		
		//Captura de excepciones de la clase y su método
		}catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	//Opcion que invoca método de mostrar lista de aulas en Aulas
	@Override
	public void listarAulas() {
		Consola.mostrarCabecera("Lista de aulas");
		List<String> aulas= controlador.representarAulas();
		if (aulas== null) {
			System.out.println(ERROR + "No existen aulas para listar. Ingrese un aula en el sistema.");
		}else {	
			
			Iterator<String> iteradorAulas= aulas.iterator();
			while (iteradorAulas.hasNext()) {
				System.out.println(iteradorAulas.next().toString());
			}
		}
			
	}
	//opción que invoca la inserción de profesores
	@Override
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {
			controlador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Profesor insertado con éxito.");
			
			//Captura de excepciones de clase y método
		}catch (NullPointerException | OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	//opción que invoca método boorar de profesores
	@Override
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		try {
			controlador.insertarProfesor(Consola.leerProfesor());
			System.out.println("Profesor borrado con éxito.");
			
			//capturamos excepciones en clase y método
		}catch (NullPointerException | OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//opción que invoca método buscar profesor en profesores
	@Override
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor;
		try {
			profesor= controlador.buscarProfesor(Consola.leerProfesor());
			String mensaje= (profesor!= null) ? profesor.toString() : ERROR+ "El profesor ingresado no está registrado en el sistema.";
			System.out.println(mensaje);
			
			//capturamos excepciones en clase y método
		}catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//opción que invoca método de ver listas de profesores en Profesores
	@Override
	public void listarProfesores() {
		Consola.mostrarCabecera("Lista de profesores");
		
		List<String> profesores= controlador.representarProfesores();
		if (profesores == null) {
			System.out.println("No hay profesores que mostrar");
		} else {
			Iterator<String> iteradorProf = profesores.iterator();
			while (iteradorProf.hasNext()) {
				System.out.println(iteradorProf.next().toString());
			}
		}
	}
	
	//opción que invoca método de insertar reserva
	@Override
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		try {
			Profesor profesor= Consola.leerProfesorFicticio();
			Profesor profesorReg= controlador.buscarProfesor(profesor);
			if (profesorReg != null) {
				Aula aula= Consola.leerAulaFicticia();
				Aula aulaReg= controlador.buscarAula(aula);
				
				if (aulaReg!= null) {
					Permanencia permanencia= Consola.leerPermanencia();
					Reserva reserva= new Reserva(profesorReg, aulaReg, permanencia);
					controlador.realizarReserva(reserva);
					
					System.out.println("Reserva realizada. " + NOMBRE_VALIDO + "   " +  CORREO_VALIDO );
				} else {
					System.out.println(ERROR + "El aula" + aula.getNombre() + " no est� registrada.");
				}
			} else {
				System.out.println(ERROR + "El correo " + profesor.getCorreo() + " no est� registrado.");
			}
			//Captura de excepciones
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	// método de Consola "leerNombreProfesor.
	//método utilizado para ahorrar información no necesaria
	/*
	private Reserva leerReserva(Profesor profesor) {
		Consola.mostrarCabecera("Realizar reserva");
		
		String nombreAula;
		String nombreProfesor;
		List<String> profesores= controlador.representarProfesores();
		List<String> aulas= controlador.representarAulas();
		String correoProfesorCt= new String();
		String correoProfesorSt= new String();
		String telefonoProf= new String();
		String datosProf= null;
		
		//inicialización de variables
		Reserva reserva= null;
		Aula aula= null;
		Permanencia permanencia= null;
		boolean aulaRegistrado= false;
		boolean profesorRegistrado= false;
		boolean telefonoRegistrado= false;
		
		try {
			nombreProfesor= Consola.leerNombreProfesor();
			nombreAula= Consola.leerNombreAula();
			
			//recorrer array y mostrar infrormación de profesores. Guarda información en cada array como parámetro
			for (Iterator<String> iteradorLeerRes= profesores.iterator(); iteradorLeerRes.hasNext();) {
				String infoProfesores= iteradorLeerRes.next();
				
				//sentencia condicional para comparar el nombre del profesor introducido con
				//el resultado de profesores con toString.
				//Validación a través del método indexof
				
				if (nombreProfesor.equalsIgnoreCase(infoProfesores.substring(infoProfesores.indexOf('=') +1, infoProfesores.indexOf(',') ))){
					datosProf= infoProfesores;
					profesorRegistrado= true;
				}
			}
			if (datosProf != null) {
				if (datosProf.contains("telefono")) {
					telefonoRegistrado= true;
				}
				if(telefonoRegistrado) {
				
				
				//A través del mismo método se obtiene información como correo especificando la posición de la cadena donde se encuentra correo
				
						correoProfesorCt= datosProf.substring(datosProf.indexOf('=') +1, datosProf.lastIndexOf(',') );
						telefonoProf=datosProf.substring(datosProf.lastIndexOf('=') +1);		
				}else {
				correoProfesorSt=datosProf.substring(datosProf.lastIndexOf('=')+1);
			}
		}
		for (Iterator<String> iteradorLeerRes= aulas.iterator(); iteradorLeerRes.hasNext();) {
			String nombreAulas= iteradorLeerRes.next();
			if(nombreAulas.toString().replace("nombre Aula=", "").equals(nombreAula)) {
				aula= new Aula(aula);
				aulaRegistrado= true;
				}
		}
		
		if (!aulaRegistrado) {
			System.out.println(ERROR + "No se ha podido encontrar el profesor en el sistema.");
		}

		if (!aulaRegistrado) {
			System.out.println(ERROR + "No se ha podido encontrar el aula en el sistema.");

		} else if (profesorRegistrado && aulaRegistrado && !telefonoRegistrado) {
			permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());

			Profesor profesorALeer = new Profesor(nombreProfesor, correoProfesorSt);

			reserva = new Reserva(profesorALeer, aula, permanencia);
			
		} else if (profesorRegistrado && aulaRegistrado && telefonoRegistrado) {
			permanencia = new Permanencia(Consola.leerDia(), Consola.leerTramo());

			Profesor profesorALeer = new Profesor(nombreProfesor, correoProfesorCt);

			reserva = new Reserva(profesorALeer, aula, permanencia);
		}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}

		return reserva;
	}		
	*/
	//opción para borrar reserva
	@Override
	public void anularReserva() {
		Consola.mostrarCabecera("Anular Reserva");
		
		try {
			controlador.anularReserva(Consola.leerReservaFicticia());
			System.out.println("Reserva anula con �xito, " + NOMBRE_VALIDO + CORREO_VALIDO + ".");
			//Captura de excepciones
		}catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	//listar reservas a través del método listar en Reservas
	@Override
	public void listarReservas() {
		Consola.mostrarCabecera("Listas de Reservas");
			
		List<String> reservas= controlador.representarReservas();
		if (reservas.size()> 0) {
			for (Iterator<String> iteradorLr = reservas.iterator(); iteradorLr.hasNext();) {
				String reserva = iteradorLr.next();
				System.out.println(reserva);
			}
		}else {
			System.out.println("No existen reservas en la lista. Ingrese una reserva.");
		}
	}
	//método para listar reservas con el parámetro aulas invocado en Reservas
	@Override
	public void listarReservasAula() {
		Consola.mostrarCabecera("Lista de reservas por aula");
		List<Reserva> reservas= controlador.getReservasAula(Consola.leerAula());
		if (reservas.size() > 0) {
			for (Iterator<Reserva> iteradorLrA = reservas.iterator(); iteradorLrA.hasNext();) {
				Reserva reserva = iteradorLrA.next();

				System.out.println(reserva);
			}
		}else {
			System.out.println("No existen reservas para este aula.");
		}
	}
	//invocar método listar reservas por profesor en Reservas
	@Override
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Lista de reservas por profesor");
		List<Reserva> reservas= controlador.getReservasProfesor(Consola.leerProfesor());
		if (reservas.size() > 0) {
			for (Iterator<Reserva> iteradorLrP = reservas.iterator(); iteradorLrP.hasNext();) {
				Reserva reserva = iteradorLrP.next();

				System.out.println(reserva);
			}
		}else {
			System.out.println("No existen reservas para este profesor");
		}
	}
	
	//invocación de método consultar disponibilidad en Reservas
	@Override
	public void consultarDisponibilidad() {
		
		try {
			Aula aula= Consola.leerAulaFicticia();
			Aula aulaReg= controlador.buscarAula(aula);
			if (aulaReg != null) {
				Permanencia permanencia= Consola.leerPermanencia();
				if(controlador.consultarDisponibilidad(aula, permanencia)){
					System.out.println(" El aula " + aula.getNombre() + " est� disponible para " + permanencia);
				}else {
					System.out.println(" El aula " + aula.getNombre() + " no est� disponible para " + permanencia);
				}
			}else {
				System.out.println(ERROR + " El aula " + aula.getNombre() +" no existe.");
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
}

