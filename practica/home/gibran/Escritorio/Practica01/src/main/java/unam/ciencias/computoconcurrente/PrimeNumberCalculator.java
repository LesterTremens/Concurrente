package unam.ciencias.computoconcurrente;
import java.lang.Math;
public class PrimeNumberCalculator implements Runnable{
    private int inicio,fin;
    private int threads;
    private static int numPrimo;
    public static boolean result;
    public static int longitudSubInter; //Dividimos el intervalo [2,N-1] en this.threads cantidad de sub interbalos, uno por cada hilo


    public PrimeNumberCalculator() {
        this.threads = 1;
    }

    public PrimeNumberCalculator(int threads) {
        this.threads = threads > 1 ? threads : 1;
    }
    

    public boolean isPrime(int n) throws InterruptedException{
	result=true;
	PrimeNumberCalculator P = new PrimeNumberCalculator(threads);
	this.numPrimo=n;
	this.longitudSubInter=(int)numPrimo/threads;
	if(n==0 || n==1 || n==-1){
		result= false;
		return result;
	}
	Thread[] hilos = new Thread[threads];
	if(numPrimo<0){
		numPrimo=numPrimo*(-1);
	}
	for(int i=0; i<threads; i++){
		hilos[i] = new Thread(this,"hilo"+i);
		if(i==0){
			this.inicio = 2;
			if ((longitudSubInter*(i+1)) >= numPrimo) {
				this.fin = numPrimo-1;		
			}else{
				this.fin = 2+longitudSubInter;
			}
			hilos[i].start();
			hilos[i].join();
		}else if(i==threads-1){
			this.inicio = 2+(longitudSubInter*(i+1));
			this.fin = numPrimo-1;
			hilos[i].start();
			hilos[i].join();
		}else{
			this.inicio = 2+(longitudSubInter*(i));
			if ((longitudSubInter*(i+1)) >= numPrimo) {
				this.fin = numPrimo-1;		
			}else{
				this.fin = 2+(longitudSubInter*(i+1))-1;
			}
			hilos[i].start();
			hilos[i].join();
		}
		
	}
	
        return result;

    }
    

    @Override
    public void run(){
	try{	
		for(int i = inicio; i<=fin; i++){
			if(numPrimo%i==0){
				this.result=false;
			}
		}
	}catch(Exception e){};
    }
}
