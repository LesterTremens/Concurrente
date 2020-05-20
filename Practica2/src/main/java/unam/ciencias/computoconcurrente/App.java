package unam.ciencias.computoconcurrente;

public class App {

    public static void main(String[] a) throws InterruptedException {
    	int count = 5;
    	String[] nombres = {"Orlando", "Juan", "Mauricio", "Alain", "Javier"};

    	Palillo[] palillos = new Palillo[count];
    	Filosofo[] filosofos = new Filosofo[count];

    	for(int i = 0; i < palillos.length; i++){
    		palillos[i] = new Palillo(i);
    	}

    	for (int i = 0; i < filosofos.length; i++){
    		if( i != filosofos.length-1){
    			filosofos[i] = new Filosofo(palillos[i], palillos[i+1], nombres[i]);
                //System.out.println(filosofos[i].nombre);
    			filosofos[i].start();
    		} else {
    			filosofos[i] = new Filosofo(palillos[0], palillos[i], nombres[i]);
                //System.out.println(filosofos[i].nombre);
    			filosofos[i].start();
    		}
    	}
        
    }
}
