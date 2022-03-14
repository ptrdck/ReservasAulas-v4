package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores{
	
	//Inicialización de Lista (0..*)
	private List<Profesor> coleccionProfesores;
	
	//Constructor actualizaco con Lista
	public Profesores() {
		coleccionProfesores= new ArrayList<>();
	}
	//Constructor copia
	public Profesores(IProfesores profesores) {
		setProfesores(profesores);
	}
	@Override
	public void comenzar() {
		
	}
	
	@Override
	public void terminar() {
		
	}
	private void setProfesores(IProfesores profesores) {
		if (profesores== null) {
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		}
		//Asignación que evita Aliasing
		coleccionProfesores= copiaProfundaProfesores(profesores.getProfesores());
		
	}
	//método para construir copia profunda del ArrayList. 
	//Un paso más para evitar el Aliasing
	private List<Profesor> copiaProfundaProfesores(List<Profesor> profesores) {
		List<Profesor> copiaProfesores= new ArrayList<>();
		//Iterador para recorrer
		Iterator<Profesor> iteradorProfesores = profesores.iterator();
		//método while para correr el iterador
		while (iteradorProfesores.hasNext()) {
			copiaProfesores.add(new Profesor(iteradorProfesores.next()));
		}
		
		return copiaProfesores;	
	}
	@Override
	public List<Profesor> getProfesores(){
		return copiaProfundaProfesores(coleccionProfesores);
	}
	//método que retorna el tamaño del arrayList coleccionProfesores
	@Override
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}
	
	
	//método insertar profesor. Si profesor no es nulo, el método recorre coleccionProfesores hasta buscar 
	//una coincidencia. 
	@Override
	public void insertar(Profesor profesor) throws OperationNotSupportedException {
		
		if (profesor== null) {
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		}else if(!coleccionProfesores.contains(profesor)) {
			coleccionProfesores.add(new Profesor(profesor));
		}else {
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese nombre.");
		}
	}
	
	//Método buscar. Busca profesor como parámetro a través de indice indexOF. 
	@Override
	public Profesor buscar(Profesor profesor) {
		if (profesor== null) {
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		}
		//condición que busca con contains si es que profesor existe en la lista
		else if (coleccionProfesores.contains(profesor)) {
			return new Profesor(profesor);
		}else {
		return null;
		}
	}
	
	//método para borrar profesor a través de verificar que existe y luego lo eliminamos con remove
	@Override
	public void borrar(Profesor profesor) throws OperationNotSupportedException {
		if (profesor== null) {
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		}
		else if (coleccionProfesores.contains(profesor)) {
			coleccionProfesores.remove(profesor);
		} else {
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese nombre.");
		}
	}
	
	//Método representar para crear un ArrayList adecuado para guardar toString de profesores
	//ArrayList tipo String
	@Override
	public List<String> representar() {
		
		List<String> representacion= new ArrayList<>();
		Iterator<Profesor> iteradorProfesores= coleccionProfesores.iterator();
		while(iteradorProfesores.hasNext()) {
			representacion.add(iteradorProfesores.next().toString());
		}
		return representacion;
	}
}