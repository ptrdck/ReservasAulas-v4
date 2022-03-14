package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;

public class Reservas implements IReservas{
	
	
	private static final float MAX_PUNTOS_PROFESOR_MES= 200.0f;
	//inicializacion coleccionReservas (0.....*)
	private List<Reserva> coleccionReservas;
	
	//Constructor 
	public Reservas() {
		coleccionReservas= new ArrayList<>();
	}
	
	//Constructor copia
	public Reservas(IReservas reservas) {
		setReservas(reservas);
	}
	
	@Override
	public void comenzar() {
		
	}
	
	@Override
	public void terminar() {
		
	}
	//Método setreservas. Convierte un objeto de reservas en un arraylist asignando a coleccion
	private void setReservas(IReservas reservas) {
		if(reservas== null) {
			throw new NullPointerException("ERROR: No se puede copiar una reserva nula.");
		}
		coleccionReservas= copiaProfundaReservas(reservas.getReservas());	
	}
	//Método para crear una copia profunda del arraylist. 
	//solucion a Aliasing
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> copiaReservas= new ArrayList<>();
		Iterator<Reserva> iteradorReservas= reservas.iterator();
		while(iteradorReservas.hasNext()) {
			copiaReservas.add(new Reserva(iteradorReservas.next()));
		}
		return copiaReservas;
	}

	//Método Get Compara aula y permanencia, luego ordena las reservas por dichos par�metros
	@Override
	public List<Reserva> getReservas() {
		List<Reserva> reservasOrden= copiaProfundaReservas(coleccionReservas);
		Comparator<Aula> comparadorAula= Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia= (Permanencia p1, Permanencia p2)->{
			int compPermanencia= -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					compPermanencia= Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(),((PermanenciaPorTramo) p2).getTramo().ordinal());
				}else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					compPermanencia= ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
				}
			} else {
				compPermanencia= p1.getDia().compareTo(p2.getDia());
			}
			return compPermanencia;
		};
		reservasOrden.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
		return reservasOrden;
						
	}
	
	//método para retornar tamaño de arraylist de colección
	@Override
	public int getNumReservas() {
		return coleccionReservas.size();
	}
	//método insertar. Comprobación de nulos. Recorre coleccionReservas buscando alguna existencia. 
	//Retorna excepción si Reserva está duplicada. 
	//Retorna una copia de Reserva como parámetro.
	@Override
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva== null) {
			throw new NullPointerException("ERROR: No se puede realizar una reserva nula.");
		}
		//inicializaci�n de variable que comprobar� la existencia del aula.
		Reserva existe= getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		
		if (existe!= null) {
			
			//b�squeda de reserva
			if (existe.getPermanencia() instanceof PermanenciaPorTramo && reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este d�a.");
			}
			if (existe.getPermanencia() instanceof PermanenciaPorHora && reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException ("ERROR: Ya se ha realizado una reserva de otro tipo de permanencia para este d�a.");
			}
		}
		
		//validaci�n de mes
		
		if(!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("ERROR: S�lo se pueden hacer reservas para el mes que viene o posteriores.");
		}		
		if (getPuntosGastadosReserva(reserva)> MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("ERROR: Esta reserva excede los puntos m�ximos por mes para dicho profesor.");
		}
		if (coleccionReservas.contains(reserva)) {
			throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
		}
		else {
			coleccionReservas.add(new Reserva(reserva));
		}
	}

	//Método que valida mes de fecha de reserva sólo si la reserva se realiza con posterioridad al mes actual
	//método compare to que devolverá true si el mes de reserva es posterior 
	//al mes donde se quiere realizar la reserva
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		if (reserva== null) {
			throw new NullPointerException("ERROR La reserva no puede ser nula.");
		}
		//bandera
		boolean mesSiguiente= false;
		Month mes= reserva.getPermanencia().getDia().getMonth();
		Month mesHoy= LocalDate.now().getMonth();
		if (mes.compareTo(mesHoy)>0) {
			mesSiguiente=true;
		} else {
			mesSiguiente= false;
		}
		return mesSiguiente;
		
	}
	// m�todo que cuenta los puntos gastados por reserva. 
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntos = 0;
		for (Iterator<Reserva> it = getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia()).iterator(); it.hasNext();) {
			Reserva reservaProfesor = it.next();
			puntos += reservaProfesor.getPuntos();
		}
		return puntos + reserva.getPuntos();
	}
	
	//Método que ingresa profesor y mes para retornar el mes de la reserva.
	//devolverá la fecha como mes
	//Extrae mes de reserva como también LocalDate como parámetro y comparará profesor de reserva
	//Compara meses. Si ambos coinciden entonces copia la reserva en la lista creada. 
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate fecha){
		if (profesor== null) {
			throw new NullPointerException("ERROR: No se pueden buscar reservas de un profesor nulo.");
		}
		
		List<Reserva> reservasMes= new ArrayList<>();
		Iterator<Reserva> iteradorMes= coleccionReservas.iterator();
		while (iteradorMes.hasNext()) {
			Reserva reserva= iteradorMes.next();
			Month mesAL= reserva.getPermanencia().getDia().getMonth();
			Month mesFecha= fecha.getMonth();
			if( (profesor.equals(reserva.getProfesor())) && (mesAL.compareTo(mesFecha)== 0) ) {
				reservasMes.add(new Reserva(reserva));	
			}
		}
		return reservasMes;
	}
	
	//método análogo a ReservaProfesorMes
	public Reserva getReservaAulaDia(Aula aula, LocalDate fecha) {
		if (aula== null) {
			throw new NullPointerException("ERROR: No se puede buscar reserva para un d�a nulo.");
		}
		Reserva reservaAD=null;
		Iterator<Reserva> iteradorReservas= coleccionReservas.iterator();
		while (iteradorReservas.hasNext()) {
			Reserva reserva= iteradorReservas.next();
			if ((aula.equals(reserva.getAula())) && (fecha.compareTo(reserva.getPermanencia().getDia())== 0)){
				reservaAD= new Reserva(reserva);
			}
		}
		return reservaAD;
	}
	
	//ACtualización de método buscar
	@Override
	public Reserva buscar(Reserva reserva) {
		
		if (reserva== null) {
			throw new NullPointerException("ERROR: No se puede buscar un reserva nula.");
		}
		
		if (coleccionReservas.contains(reserva)) {
			return new Reserva(reserva);
		} else {
			return null;
		}
	}
	
	//Contructor borrar a través del desplazamiento de elementos de array.
	@Override
	public void borrar(Reserva reserva) throws OperationNotSupportedException{
		
		if (reserva== null) {
			throw new NullPointerException("ERROR: No se puede anular una reserva nula.");
		}
		
		if (coleccionReservas.contains(reserva)) {
			coleccionReservas.remove(reserva);
		} else {
			throw new OperationNotSupportedException("ERROR: La reserva a anular no existe.");
		}
	}
	
	//Método para representación en forma de ArrayList
	@Override
	public List<String> representar() {
		
		List<String> representacion= new ArrayList<>();
		Iterator<Reserva> iteradorReserva= coleccionReservas.iterator();
		while(iteradorReserva.hasNext()) {
			representacion.add(iteradorReserva.next().toString());
		}
		return representacion;
	}
	
	//método que crea listas de reservas
	@Override
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new NullPointerException("ERROR: El profesor no puede ser nulo.");
		}
		List<Reserva> reservaListaProf= new ArrayList<>();
		Iterator<Reserva> iteradorReservas= coleccionReservas.iterator();
		while (iteradorReservas.hasNext()) {
			Reserva reserva= iteradorReservas.next();
			if(reserva.getProfesor().equals(profesor)) {
				reservaListaProf.add(new Reserva(reserva));
			}
		}
		/*
		Comparator<Aula> comparadorAula= Comparator.comparing(Aula::getNombre);
		Comparator<Permanencia> comparadorPermanencia= (Permanencia p1, Permanencia p2) -> {
			int comp= -1;
			if (p1.getDia().equals(p2.getDia())) {
				if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
					comp= Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(), ((PermanenciaPorTramo) p2).getTramo().ordinal());
				}
				else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
					comp= ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
		
				}else {
					comp= p1.getDia().compareTo(p2.getDia());
				}
				return comp;
			};
			reservaListaProf.sort(Comparator.comparing(Reserva::getAula, comparadorAula).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
			*/
			return reservaListaProf;
		}
	//método que crea listas de reservas con el parámetro aula
		@Override
		public List<Reserva> getReservasAula(Aula aula) {
			if (aula == null) {
				throw new NullPointerException("ERROR: No se puede anula una reserva nula.");
			}
			List<Reserva> reservaListaAu= new ArrayList<>();
			Iterator<Reserva> iteradorReservas= coleccionReservas.iterator();
			while (iteradorReservas.hasNext()) {
				Reserva reservaIt= iteradorReservas.next();
				if(aula.equals(reservaIt.getAula())) {
					reservaListaAu.add(new Reserva(reservaIt));
				}
				
			}
			Comparator<Permanencia> comparadorPermanencia= (Permanencia p1, Permanencia p2) ->{
				int comp= -1;
				if (p1.getDia().equals(p2.getDia())) {
					if (p1 instanceof PermanenciaPorTramo && p2 instanceof PermanenciaPorTramo) {
						comp= Integer.compare(((PermanenciaPorTramo) p1).getTramo().ordinal(), ((PermanenciaPorTramo) p2).getTramo().ordinal());
					}else if (p1 instanceof PermanenciaPorHora && p2 instanceof PermanenciaPorHora) {
						comp= ((PermanenciaPorHora) p1).getHora().compareTo(((PermanenciaPorHora) p2).getHora());
					}
					}else {
						comp= p1.getDia().compareTo(p2.getDia());
					}
					return comp;
				};
				reservaListaAu.sort(Comparator.comparing(Reserva::getPermanencia, comparadorPermanencia).thenComparing(Reserva::getPermanencia, comparadorPermanencia));
			return reservaListaAu;
		}
		//método que crea listas de reservas
		@Override
		public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
			if (permanencia == null) {
				throw new NullPointerException("ERROR: No se puede anula una reserva nula.");
			}
			List<Reserva> reservaListaPer= new ArrayList<>();
			Iterator<Reserva> iteradorReservas= coleccionReservas.iterator();
			while (iteradorReservas.hasNext()) {
				Reserva reservaIt= iteradorReservas.next();
				if(permanencia.equals(reservaIt.getPermanencia())) {
					reservaListaPer.add(new Reserva(reservaIt));
				}
			}
			return reservaListaPer;
		}
	
	//méotodo para consulta de disponibilidad de reservas con los atributos de aula y permanencia
		@Override
		public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		
		if (aula == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de un aula nula.");
		} else if (permanencia == null) {
			throw new NullPointerException("ERROR: No se puede consultar la disponibilidad de una permanencia nula.");
		}
		//bandera
		
		boolean disponible= true;
		Iterator<Reserva> iteradorDisp= coleccionReservas.iterator();
		while(iteradorDisp.hasNext()) {
			Reserva reservaDisp= iteradorDisp.next();
			if (reservaDisp.getPermanencia() instanceof PermanenciaPorTramo && permanencia instanceof PermanenciaPorHora && reservaDisp.getAula().equals(aula)
					&& reservaDisp.getPermanencia().getDia().equals(permanencia.getDia())) {
				disponible= false;
			}
			
			else if (reservaDisp.getPermanencia() instanceof PermanenciaPorTramo && permanencia instanceof PermanenciaPorTramo
					&& reservaDisp.getAula().equals(aula) && reservaDisp.getPermanencia().equals(permanencia)) {
				disponible= false;
			}
			
			else if (reservaDisp.getPermanencia() instanceof PermanenciaPorHora
					&& permanencia instanceof PermanenciaPorHora && reservaDisp.getAula().equals(aula)
					&& reservaDisp.getPermanencia().equals(permanencia)) {
				disponible= false;
			}
		}
		return disponible;
		}
}
