package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores{
	
	// fichero profesores
	private static final String NOMBRE_FICHERO_PROFESORES= "datos/profesores.dat";
	
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
	
	//m�todo comenzar
	@Override
	public void comenzar() {
		leer();
	}
	
	//crear m�todo leer
	private void leer() {
		
		File ficheroProfesores= new File(NOMBRE_FICHERO_PROFESORES);
		try  (ObjectInputStream entrada= new ObjectInputStream(new FileInputStream(ficheroProfesores))){
			Profesor profesor= null;
			do {
				profesor= (Profesor) entrada.readObject();
				insertar(profesor);
			}while (profesor!= null);
			entrada.close();
		
		}catch (ClassNotFoundException e) {
			System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero profesores.");
		}catch (EOFException e) {
			System.out.println("Fichero profesores leido satisfactoriamente");
		}catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		}catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}	
		
	//m�todo terminar
	@Override
	public void terminar() {
		escribir();
	}
		
		//creaci�n m�todo escribir
		
	private void escribir() {
		File ficherosprofesores= new File(NOMBRE_FICHERO_PROFESORES);
		try (ObjectOutputStream salida= new ObjectOutputStream(new FileOutputStream(ficherosprofesores))){
			for (Profesor profesor: coleccionProfesores)
				salida.writeObject(profesor);
			System.out.println("Fichero profesores escrito satisfactoriamente.");
			salida.close();
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero profesores.");
		}catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		}
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