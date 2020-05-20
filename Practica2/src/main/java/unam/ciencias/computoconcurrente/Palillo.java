package unam.ciencias.computoconcurrente;
import java.util.concurrent.*;

public class Palillo {
	//Creamos el semaforo para el palillo
	public Semaphore palillo = new Semaphore(1);
	public int id;

	Palillo(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	//Nos dice si el palillo está disponible
	public boolean agarrar(){

		return palillo.tryAcquire();
	}

	//Libera los palillos para que puedan ser usados
	public void soltar(){
		palillo.release();
	}
}
