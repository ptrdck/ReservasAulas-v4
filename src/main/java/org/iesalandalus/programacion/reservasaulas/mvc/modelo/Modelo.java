package org.iesalandalus.programacion.reservasaulas.mvc.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Modelo implements IModelo {
	
	//(0,1)
	private IProfesores profesores;
	private IAulas aulas;
	private IReservas reservas;
	
	//Constructor
	public Modelo(IFuenteDatos fuenteDatos) {
		profesores= fuenteDatos.crearProfesores();
		aulas= fuenteDatos.crearAulas();
		reservas= fuenteDatos.crearReservas();
	}
	
	//m�todo comenzar
	@Override
	public void comenzar() {
		aulas.comenzar();
		profesores.comenzar();
		reservas.comenzar();
	}
	
	@Override
	public void terminar() {
		aulas.terminar();
		profesores.terminar();
		reservas.terminar();
	}
	
	//método que invoca a la clase reservas
	@Override
	public List<Aula> getAulas() {
		return aulas.getAulas();
		}
	
	//tamaño de array aulas
	@Override
	public int getNumAulas() {
		
		return aulas.getNumAulas();
	}
	//devuelve información de aulas
	@Override
	public List<String> representarAulas() {
		return aulas.representar();
	}
	
	//invoca al método buscar aula
	@Override
	public Aula buscar(Aula aula) {
		return aulas.buscar(aula);
	}
	//Invoca al méotodo insertar aula
	@Override
	public void insertar(Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}
	//Invoca al método borrar aula
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}
	
	//invoca a los méotodos de profesor
	@Override
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}
	//devuelve el tamaño del array profesores
	@Override
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}
	//devuelve información de profesores
	@Override
	public List<String> representarProfesores() {
		return profesores.representar();
	}
	//invoca al método buscar profesor en profesor
	@Override
	public Profesor buscar(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	//invoca al método insertar profesor en profesor
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException{
		profesores.insertar(profesor);
	}
	//invoca al método borrar en profesor
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException{
		profesores.borrar(profesor);
	}
	//devuelve las reservas según su posición en el array
	@Override
	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}
	
	//devuelve tamaño del array reservas
	@Override
	public int getNumReservas() {
		return reservas.getNumReservas();	
	}
	//devuelve información de cada reserva
	@Override
	public List<String> representarReservas() {
		return reservas.representar();
	}
	//Invoca a método buscar en reserva
	@Override
	public Reserva buscar(Reserva reserva) {
		return reservas.buscar(reserva);
	}
	//invoca al méotodo insertar en reserva
	@Override
	public void realizarReserva(Reserva reserva) throws OperationNotSupportedException{
		reservas.insertar(reserva);
	}
	//invoca el método borrar en reserva
	@Override
	public void anularReserva(Reserva reserva) throws OperationNotSupportedException{
		reservas.borrar(reserva);
	}
	//devuelve reservas por atríbuto aula.
	@Override
	public List<Reserva> getReservasAula(Aula aula) {
		return reservas.getReservasAula(aula);
	}
	//devuelve reservas con atributo profesor
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}
	//devuelve reservas con antributo permanencia
	@Override
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}
	//invoca al método consultar disponibilidad en Reservas
	@Override
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
	
}
