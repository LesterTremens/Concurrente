package unam.ciencias.computoconcurrente;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 *  El producto de matrices debe ser paralelo.
 *
 */
public class Matrices extends Thread {
  private int row;
  private int rowP;
  private int column;
  private AtomicIntegerArray matrixA;
  private AtomicIntegerArray[] matrixB;
  private AtomicIntegerArray[] product;
  private AtomicIntegerArray[] matrixP;
  private int bool;

  /*
  * row renglones
  * col columnas
  * Matrix A a multiplicar
  * Matrix B a multiplicar
  * Producto entre A y B
  * Decide que ejecutar en run
  * Constructor para ejecutar la multiplicacion
  */
  public Matrices(final int row, final int column,
        final AtomicIntegerArray matrixA, final AtomicIntegerArray[] matrixB,
        final AtomicIntegerArray[] product, int bool) {
     this.row = row;
     this.column = column;
     this.matrixA = matrixA;
     this.matrixB = matrixB;
     this.product = product;
     this.bool = bool;
  }

  /*
   * rowP renglon para imprimir
   * colP columna para imprimir
   * matrixP matrix a imprimir
   * decide que ejecutar en el Run
   * Construstor para imprimir
   */
  public Matrices(final int rowP, final AtomicIntegerArray[] matrixP, int bool) {
      this.rowP = rowP;
      this.matrixP = matrixP;
      this.bool = bool;
   }

 /*
  * Is es 1 calcula si es 2 imprime
  * 1 Hace la multiplicacion y la coloca en el arreglo de producto
  * 2 Imprime lo que tiene matrixP
  */
  public synchronized void run() {
    if (bool == 0) {
      int value = 0;
      for (int i = 0; i < matrixA.length(); i++) {
         value = value + (matrixA.get(i) * matrixB[i].get(column));
       }
      product[row].set(column, value);

    }else if (bool == 1) {
      System.out.print("Resultado ");
      for (int i = 0; i < matrixP[rowP].length(); i++) {
        System.out.print( matrixP[rowP].get(i));

      }
      System.out.println(" ");
   }
 }
}
