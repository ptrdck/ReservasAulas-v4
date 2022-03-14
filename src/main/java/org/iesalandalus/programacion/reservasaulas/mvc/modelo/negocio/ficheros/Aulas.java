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
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {
	
	// declaraci髇 de Fichero
	
	private static final String NOMBRE_FICHERO_AULAS= "datos/aulas.dat";

	//Inicializaci贸n de arrayList (0...*)
	private List<Aula> coleccionAulas;
	
	public Aulas() {
		coleccionAulas= new ArrayList<>();
	}
	//Constructor copia
	public Aulas(IAulas aulas) {
		setAulas(aulas);
	}
	
	//m閠odo comenzar
	@Override
	public void comenzar() {
		leer();
	}
	
	//Creaci髇 m閠odo leer
	
	private void leer() {
		File ficheroAulas= new File(NOMBRE_FICHERO_AULAS);
		
		//
		try (ObjectInputStream entrada= new ObjectInputStream(new FileInputStream(ficheroAulas))){
			Aula aula= null;
			do {
				
				aula= (Aula) entrada.readObject();
				insertar(aula);
			}while (aula!= null);
			
			entrada.close();
			
		}catch (ClassNotFoundException e) {
			System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero aulas.");
		}catch (EOFException e) {
			System.out.println("Fichero aulas leido satisfactoriamente");
		}catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		}catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//M閠odo terminar
	@Override
	public void terminar() {
		escribir();
	}
	
	//creaci髇 m閠odo escribir
	
	private void escribir() {
		File ficherosAulas= new File(NOMBRE_FICHERO_AULAS);
		try { ObjectOutputStream salida= new ObjectOutputStream(new FileOutputStream(ficherosAulas));
		for (Aula aula: coleccionAulas)
			salida.writeObject(aula);
		salida.close();
		System.out.println("Fichero aulas escrito satisfactoriamente.");
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero de aulas.");
		}catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		}
	}

	//creci贸n de Setter para Aulas
	private void setAulas(IAulas aulas) {
		if (aulas== null) {
			throw new NullPointerException("ERROR:No se puede copiar aulas nulas.");
		}
		//copia de array para evitar Aliasing
		this.coleccionAulas= copiaProfundaAulas(aulas.getAulas());
	}
	
	//Crecion getter para lista Aulas, retorna una copia para coleccion
	@Override
	public List<Aula> getAulas() {
		return copiaProfundaAulas(coleccionAulas);
	}
	
	//Constructor copia en array
	private List<Aula> copiaProfundaAulas(List<Aula> aulas) {
		List<Aula> copiaAulas= new ArrayList<>();
		// Recorrer lista con Iterator. Inicializar Iterador antes del while
		Iterator<Aula> iteradorAulas= aulas.iterator();
		while(iteradorAulas.hasNext()) {
			copiaAulas.add(new Aula (iteradorAulas.next()));	
		}
		return copiaAulas;
	}
	
	//M茅todo que nos retorna el tama帽o de la colecci贸n
	@Override
	public int getNumAulas() {
		return coleccionAulas.size();
	}
	
	//m茅todo insertar aula. Comprobaci贸n de nulos. Recorre la colecci贸n cuando no es nulo en b煤squeda de alguna coincidencia. 
	//Retorna  una excepci贸n si encuentra el aula ya creada. ASigna una copia de aula 
	@Override
	public void insertar (Aula aula) throws OperationNotSupportedException {
		if (aula== null) {
			throw new NullPointerException("ERROR: No se puede insertar un aula nula.");
		}
		
		else if (buscar(aula)== null) {
			coleccionAulas.add(new Aula(aula)); //incremento
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un aula con ese nombre.");
		
		}
	}
	//m茅todo que busca el indice de un aula creada como par谩metro en el m茅todo anterior.
	@Override
	public Aula buscar(Aula aula) {
		if (aula== null) {
			throw new NullPointerException("ERROR: No se puede buscar un aula nula.");
		}
		// Buscamos si el Aula existe dentro de coleccionAulas
		if (coleccionAulas.contains(aula)) {
			return new Aula(aula);
		}else {
			return null;
		}
	}
	//Metodo borrar:
	//Localizar posici贸n de aula en el array y desplazamos posici贸n para borrar
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException{
		if (aula== null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		if (buscar(aula)== null) {
			throw new OperationNotSupportedException("ERROR: No existe ning煤n aula con ese nombre.");
		} // condici贸n si encuentra el aula buscada. Posteriormente retorna la eliminaci贸n del aula
		else if (coleccionAulas.contains(aula)) {
			coleccionAulas.remove(aula);
		}
	}
	
	//metodo para Representar. Crea ArrayList de tipo String donde se guardar谩n los datos
	//que saldr谩n de .toString de Aulas
	@Override
	public List<String> representar() {
		//incializaci贸n
		List<String> representacion=new ArrayList<>();
		// Iterador reemplazo del antiguo for
		Iterator<Aula> iteradorAulas= coleccionAulas.iterator();
		while (iteradorAulas.hasNext()) {
			representacion.add(iteradorAulas.next().toString());
		}
		return representacion;
	}
}
