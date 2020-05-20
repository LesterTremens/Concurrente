package unam.ciencias.computoconcurrente;

/**
 *  Cada filósofo se ejecuta en un hilo.
 */
public class Filosofo extends Thread {

    //public static int DEFAULT_TABLE_SIZE = 5;

	//Nombre del filosofo
    public String nombre;
    //Palillo derecho que corresponde a 
    //un semaforo.
    private Palillo derecha;
    //Palillo izq que corresponde a 
    //un semaforo.
    private Palillo izquierda;
    //contador para saber cuantas veces come cada uno.
    private int cont = 0;



	Filosofo(Palillo izquierda, Palillo derecha, String nombre){
		this.izquierda = izquierda;
		this.derecha = derecha;
		this.nombre = nombre;
	}

	
	public void run(){
		try{
			sleep(1000);
		} catch (InterruptedException e) {

		}
		while(cont < 2){

			comer();
		}
	}

	//Los filosofos agarran primero el palillo derecho y preguntan
	//si está disponible. En caso de estar disponible preguntan por el izquierdo
	//para poder comer.
	//Si no está disponible el izquierdo, suelta el palillo derecho.
	public void comer(){
		
		//Aqui es cuando el filosofo entra a la seccion critica 
		if (derecha.agarrar()){
			if(izquierda.agarrar()){
				try{
					System.out.println(nombre +" está comiendo xD");
					sleep(2000);
					
				}catch(InterruptedException e) {

				}
				izquierda.soltar();
				derecha.soltar();
				System.out.println("Terminó de comer "+ nombre);
				cont++;
			} else {
				derecha.soltar();
			}

		}
	}

}