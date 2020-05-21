package unam.ciencias.computoconcurrente;

public class MatrixUtils implements Runnable{
    private int threads;
    private static double[] listaPromedios;
    private static int[] matrizGlobal; 
    private static int secciones; 

    public MatrixUtils() {
        this.threads = 1;
    }

    public MatrixUtils(int threads) {
        this.threads = threads;
    }

    @Override
    public void run() {
        double promLocal = 0;
        String ID = Thread.currentThread().getName();
        int hebra = Integer.valueOf(ID);
        int seccion1 = 0;

        
        if (matrizGlobal.length - ((hebra+1)*secciones -1) < secciones) {
            for (int i = hebra*secciones; i < matrizGlobal.length; i++) {
                promLocal += matrizGlobal[i];

                seccion1 += 1;

                
            } 
            promLocal = promLocal /seccion1;

            
        }else{

            for (int i = hebra*secciones; i < (hebra+1)*secciones; i++) {
                promLocal += matrizGlobal[i];

                seccion1 += 1;

                

            }    
            
            promLocal = promLocal / seccion1;
            
                
        }
       
        listaPromedios[hebra] = promLocal;

        
        
    }

    public double findAverage(int[][] matrix) throws InterruptedException{
        
        if (this.threads == 1) {
            return Average(matrix);

        }else{
            
           listaPromedios = new double[threads];
            
            secciones = matrix.length*matrix[0].length/threads;
            
            matrizGlobal = new int[matrix.length*matrix[0].length]; 
            
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    matrizGlobal[i*matrix[0].length + j] = matrix[i][j]; 
                }
            }

            
            for (int i = 0; i < this.threads; i++) {
                Thread t = new Thread(new MatrixUtils());
                t.setName(String.valueOf(i)); 
                t.start();
                t.join();
            }

            
            double promedioGlobal = 0;
           
            for (int i = 0; i < listaPromedios.length; i++) {
                promedioGlobal += listaPromedios[i];
                

            }
            
            return promedioGlobal/listaPromedios.length;
        }
    }

    /**
     * Metodo que recorre una matriz de dos dimensiones 
     * @param matrix - matriz de dos dimensiones 
     * @return promedio - promedio de la matriz
     */
    public static double Average(int[][] matrix){
        double total = 0;
        double promedio =0;
        double divisor = 0;
        
        for(int i=0; i<matrix.length; i++){
            for (int j=0;j<matrix[i].length;j++ ) {
       
                total +=  matrix[i][j];
            }   
            divisor = matrix[i].length * matrix.length;          
            promedio = total / divisor; 
        }
  
        return promedio;
    }
}
