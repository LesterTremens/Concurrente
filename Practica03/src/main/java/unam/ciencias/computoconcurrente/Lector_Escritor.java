package unam.ciencias.computoconcurrente;

/**
 *Alfaro Jimenez Juan Adolfo
 *Apipilhuasco RosasOrlando Alain
 *Salas Martinez Mauricio Javier
 */
import java.util.*;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Lector_Escritor{
//clase para escritor
private class Escritor extends Thread{
    //entero random para que se note un poco que tambien los escritores entran y no sea acaparado por los lectores
    private Random rdm = new Random();
    //Monitor que asegura que solo un escritor entre o solo los lectores esten leyendo
    private Monitor m;
    //identificador de cada escritor para que se vea que son diferentes y visualizar un poco su comportamiento
    private int id;

    public Escritor(Monitor m, int id){
        this.m = m;
	this.id= id;
    }
	
    public void run(){
	//aqui ponemos que cada que pueda el escritor intentara entrar a leer hasta que terminems el programa
        while(true){
	    try{
		//una vez entre se esperara dentro un tiempo aleatorio simulando hacer algo 
	        m.entraE(id);
		Thread.sleep(rdm.nextInt(100));
		//cuando sale tambien espera un tiempo aleatorio para permitir que los escritores tambien entren y no acaparar
	        m.saleE(id);
		Thread.sleep(rdm.nextInt(300));
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
        }

    }	
}
//clase que implementa a los lectores
private class Lector extends Thread{
    // numero aleatorio que hara que se tarde mas y se muestre mejor la representacion
    private Random rdm = new Random();
    //asegurara que solo entre algun escritor o puedan entrar todos los lectores que quieran
    private Monitor m;
    private int id;

    public Lector(Monitor m, int id){
        this.m = m;
	this.id= id;
    }
	
    public void run(){
	//el escritor intentara escribir todas las veces hasta que terminesmos el progrma
        while(true){
	    try{
		Thread.sleep(rdm.nextInt(100));
	        m.entraL(id);
	        m.saleL(id);
		Thread.sleep(rdm.nextInt(300));
	    }catch(InterruptedException e){
		e.printStackTrace();
	    }
        }

    }	
}
//clase Monitor que se asegurara de solo permitir pasar a un escritor o varios lectores
private class Monitor {
    public boolean escritorActivo = false;	
    public int lectores=0;
	
    public synchronized void entraL(int id) throws InterruptedException{
	//mientrad haya un escritor escribiendo nadie puede entrar
	while(escritorActivo){
	    wait();	
	}
	//una vez lo anterior los un lector podra entrar aumentamos la variable que avisara a escritores cuando no haya lectores
	lectores++;
        //imprimimos el id y el lector que esta entrando
	System.out.println("Lector "+id+" esta leyendo");
    }
    //aqui el lector sale imprimimos en pantalla lo ocurrido disminuimos el numero de lectores
    //y en caso de acabarse los lectores notificamos a los escritores
    public synchronized void saleL(int id){

	System.out.println("Lector "+id+" dejo de leer");
	lectores--;
	if(lectores==0) notify();
    }
    //aqui nos aseguramos que no haya lectores ni escritores, esperamos y despues un escritor puede entrar
    public synchronized void entraE(int id) throws InterruptedException{
	
	while(escritorActivo || lectores>0 ){
	    wait();	
	}
	//una vez dentro el escritor cambia el estado para que nadie mas entre y notificamos lo sucedido imprimiendo en pantalla
	escritorActivo=true;
	System.out.println("Escritor "+id+" esta escribiendo");
    }
    //al salir solo notificamos y cambiamos el estado de escritor activo y notificamos a todos los que estan esperando entrar
    public synchronized void saleE(int id){

	System.out.println("Escritor "+id+" dejo de escribir");
	escritorActivo=false;
	notifyAll();
    }

}
    // creamos el monitor y los arreglos de escritores y lectores (aqui podemos modificar cuantos tendremos de cada uno)
    public void empieza(int esc, int lec){
	Monitor monitor = new Monitor();
	Escritor[] escritores = new Escritor[esc];
	Lector[] lectores = new Lector[lec];
	//llenamos el arreglo con cada Escritor nuevo
	for(int i=0; i<escritores.length; i++){
	    escritores[i] = new Escritor(monitor,i);
	}
	//llenamos el arreglo de Lectores
	for(int i=0; i<lectores.length; i++){
	    lectores[i] = new Lector(monitor,i);
	}
	//los siguientes dos for comenzamos la ejecucion cuncurrente de todos
	for(int i=0; i<escritores.length; i++){
	    escritores[i].start();
	}

	for(int i=0; i<lectores.length; i++){
	    lectores[i].start();
	}
    }
    //clase main solo hacemos un ejemplar de la case y corremos el metodo anterior
    public static void main(String[] args) throws IOException{
	Lector_Escritor ejemplar = new Lector_Escritor();
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader br = new BufferedReader (isr);
	System.out.println("Introduce el numero de escritores");
	int esc = Integer.parseInt(br.readLine());
	System.out.println("Introduce el numero de lectores");
	int lec = Integer.parseInt(br.readLine());
	ejemplar.empieza(esc,lec);
    }
}
