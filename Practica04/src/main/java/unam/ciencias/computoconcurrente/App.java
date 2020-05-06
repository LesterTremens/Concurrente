package unam.ciencias.computoconcurrente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;

/*
* Clase principal donde se ejecutan los problemas de
* multiplicacion de matrices y diametro de una grafica.
*/
public class App {

  private static AtomicIntegerArray[] matrixA; //Se usan porque pueden modificar simultanemente
  private static AtomicIntegerArray[] matrixB;
  private static AtomicIntegerArray[] product;
  private static int nodos; //Nodos de la grafica
  private static double[][] grafica; //Grafica expresada en forma de matrix
  private static ExecutorService executorService; //Proporciona metodos para syncronizacion asincrona de tareas
  private static int numThreads = 10; //numero de thread usados

  /*
  * Inicializamos el producto de matrices
  */
  public static void initialize() throws Exception {
      // Dimensiones de la matrix A dadas por el usuario
      System.out.println("Inciando Problema 2");
      System.out.println( "Pon el numero de lineas y columnas de la matrix A");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String line = br.readLine();

      int rowA = Integer.parseInt(line.split(" ")[0]);
      int colA = Integer.parseInt(line.split(" ")[1]);


      // Dimensiones de la matrix B dadas por el usuario
      System.out.println( "Pon el numero de lineas y columnas de la matrix B");
      line = br.readLine();
      int rowB = Integer.parseInt(line.split(" ")[0]);
      int colB = Integer.parseInt(line.split(" ")[1]);


      // Revisa si se puede realizar la multiplicacion
      if (colA != rowB) {
         throw new Exception("invalid matrix dimensions : cannot multiply");
      }

      // Creamos la matrix A
      matrixA = new AtomicIntegerArray[rowA];
      for (int i = 0; i < rowA; i++) {
         System.out.println("Pon renglones para A");
         AtomicIntegerArray row = new AtomicIntegerArray(colA);
         line = br.readLine();
         for (int j = 0; j < colA; j++) {
            row.set(j, Integer.parseInt(line.split(" ")[j]));
         }
         matrixA[i] = row;
      }

      // Creamos la matrix B
      matrixB = new AtomicIntegerArray[rowB];
      for (int i = 0; i < rowB; i++) {
         System.out.println("Pon renglones para B");
         AtomicIntegerArray row = new AtomicIntegerArray(colB);
         line = br.readLine();
         for (int j = 0; j < colB; j++) {
            row.set(j, Integer.parseInt(line.split(" ")[j]));
         }
         matrixB[i] = row;
      }

      // Inicializamos el producto de la matrix
      product = new AtomicIntegerArray[rowA];
      for (int i = 0; i < rowA; i++) {
         AtomicIntegerArray row = new AtomicIntegerArray(colB);
         for (int j = 0; j < colB; j++) {
            row.set(j, 0);
         }
         product[i] = row;
      }
   }

  /*
  * Inicializamos el problema de Diametro de una grafica.
  */
  public static void initialize2() throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String line;
    System.out.println("Inciando Problema 2");
    System.out.println("Dame los nodos de la grafica");
    line = br.readLine();
    nodos = Integer.parseInt(line.split(" ")[0]);
    grafica = new double[nodos][nodos];
    for(int i  = 0; i < nodos; i ++){
      System.out.println("Dame las relaciones para el nodo " + i + "dados los " + 4 + "nodos");
      for(int j = 0; j< nodos; j ++){
        System.out.println("Relacion (" + i + "," + j + ")");
        line = br.readLine();
        double v = Double.parseDouble(line.split(" ")[0]);
        if(v  == 0.0){
          grafica[i][j] = Double.POSITIVE_INFINITY;
        }else{
          grafica[i][j] = v;
        }
      }
    }
  }

  /*
  * Metodo principal donde Inicializamos y ejecutamos los problemas
  */
  public static void main(String[] a) throws InterruptedException {
    try{

      initialize();
      //Recorremos la matrixA para multiplicarla con la B
      for (int i = 0; i < matrixA.length; i++) {
            for (int j = 0; j < matrixB[i].length(); j++) {
               // spawn thread for row-wise multiplication
               Matrices multiplyThread = new Matrices(i, j,matrixA[i], matrixB, product,0);
               multiplyThread.start();
            }
         }

      //Imprimimos resultados de la multiplicacion
      for (int i = 0; i < product.length; i++) {
          // spawn thread for row-wise display
          Matrices displayThread = new Matrices(i, product,1);
          displayThread.start();
      }


      initialize2();
      executorService = Executors.newFixedThreadPool(numThreads);
      //Llamamos a la clase grafica para resolver el problema
      Grafica g = new Grafica(nodos,grafica,executorService, numThreads);
      g.solve();
      //Listamos los caminos y elejimos el mas largo como el diametro
      double contador = 0;
      double[] tmp = new double[3];
      for (int i  = 0; i < nodos ; i++) {
        for (int j = 0;j < nodos ;j++) {
          double resultado = g.shorestPathLength(i,j);
          System.out.println("El camino ("+ i + "," + j + ") es de tamaño " +resultado);
          if (resultado > contador) {
            contador = resultado;
            tmp[0] =i;
            tmp[1] =j;
            tmp[2] =resultado;
          }
        }
      }
      System.out.println("El diametro es de tamaño " + tmp[2] + " por el camino ("+ tmp[0] + "," + tmp[1] + ")" );
      executorService.shutdown();

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
