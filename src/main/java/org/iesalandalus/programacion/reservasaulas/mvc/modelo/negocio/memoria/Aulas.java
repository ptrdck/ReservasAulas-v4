package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;

public class Aulas implements IAulas {

	//Inicialización de arrayList (0...*)
	private List<Aula> coleccionAulas;
	
	public Aulas() {
		coleccionAulas= new ArrayList<>();
	}
	//Constructor copia
	public Aulas(IAulas aulas) {
		setAulas(aulas);
	}
	@Override
	public void comenzar() {
		
	}
	
	@Override
	public void terminar() {
		
	}

	//creción de Setter para Aulas
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
	
	//Método que nos retorna el tamaño de la colección
	@Override
	public int getNumAulas() {
		return coleccionAulas.size();
	}
	
	//método insertar aula. Comprobación de nulos. Recorre la colección cuando no es nulo en búsqueda de alguna coincidencia. 
	//Retorna  una excepción si encuentra el aula ya creada. ASigna una copia de aula 
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
	//método que busca el indice de un aula creada como parámetro en el método anterior.
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
	//Localizar posición de aula en el array y desplazamos posición para borrar
	@Override
	public void borrar(Aula aula) throws OperationNotSupportedException{
		if (aula== null) {
			throw new NullPointerException("ERROR: No se puede borrar un aula nula.");
		}
		if (buscar(aula)== null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
		} // condición si encuentra el aula buscada. Posteriormente retorna la eliminación del aula
		else if (coleccionAulas.contains(aula)) {
			coleccionAulas.remove(aula);
		}
	}
	
	//metodo para Representar. Crea ArrayList de tipo String donde se guardarán los datos
	//que saldrán de .toString de Aulas
	@Override
	public List<String> representar() {
		//incialización
		List<String> representacion=new ArrayList<>();
		// Iterador reemplazo del antiguo for
		Iterator<Aula> iteradorAulas= coleccionAulas.iterator();
		while (iteradorAulas.hasNext()) {
			representacion.add(iteradorAulas.next().toString());
		}
		return representacion;
	}
}
