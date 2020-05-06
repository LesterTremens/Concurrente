package unam.ciencias.computoconcurrente;

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.concurrent.Callable;
 import java.util.concurrent.ExecutionException;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Future;
 import java.util.concurrent.Executors;

/**
*  El calculo de diametro de G debe ser paralelo
* usando un floywarshall paralelo
*/
public class Grafica{
  private ExecutorService exec;
  private int numThreads;
  private double[] current;
  private double[] next;

  private int[] maxIndex;
  private int numNodes;
  private boolean solved;

  private int getIndex(int i, int j){
    return i*numNodes+j;
  }

  private int getI(int index){
    return index / numNodes;
  }

  private int getJ(int index){
    return index % numNodes;
  }

  /**
   * numNodos Numero de nodos en la grafica
   * distances distancia entre los nodos, van desde  0 al numero de nodos -1
   * distances[i][j] costos de la grafica c
   * Double.POSITIVE_INFINITY si es cero osea que no existe costo
   * numThreads numero de hilos a usar
   */
  public Grafica(int numNodes, double[][] distances,
                               ExecutorService exec, int numThreads){
    this.exec = exec;
    this.numThreads = numThreads;
    this.numNodes = numNodes;
    this.current = new double[numNodes*numNodes];
    this.next = new double[numNodes*numNodes];
    this.maxIndex = new int[numNodes*numNodes];
    Arrays.fill(maxIndex, -1);
    for(int i = 0; i < numNodes; i++){
      for(int j = 0; j < numNodes; j++){
        current[getIndex(i,j)] = distances[i][j];
      }
    }
    this.solved = false;
  }

 /*
  * Asgina Tareas
  */
  public void solve(){
    if(solved){
      throw new RuntimeException("Already solved");
    }
    //Va agregando trabajos dado el numero de Nodos
    for(int k = 0; k < numNodes; k++){
      List<Callable<Boolean>> tasks = new ArrayList<Callable<Boolean>>();
      if(current.length < numThreads){
        for(int i = 0; i < current.length; i++){
          tasks.add(new FloydJob(i,i+1,k));
        }
      }
      else{
        for(int t = 0; t < numThreads; t++){
          int lo = t*current.length/numThreads;
          int hi = (t+1)*current.length/numThreads;
          tasks.add(new FloydJob(lo,hi,k));
        }
      }
      try {
        List<Future<Boolean>> results = this.exec.invokeAll(tasks);
        for(Future<Boolean> result : results){
          if(!result.get().booleanValue()){
            throw new RuntimeException();
          }
        }
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      }
      double[] temp = current;
      current = next;
      next = temp;
    }
    next = null;
    solved = true;
  }

  /**
   * i renglon del arreglo current
   * j renglon del arreglo current
   * El tamaño del camino mas corto de i a j
   * Si i y j son iguales se da el camino mas corto desde el nodo i
   * Regresa Double.POSITIVE_INFINITY si no hay un camino de i a j.
   */
  public double shorestPathLength(int i, int j){
    if(!solved){
      throw new RuntimeException("Must solve first");
    }
    return this.current[getIndex(i,j)];
  }
  /**
   * Busca la ruta mas corta de  i a j si hay mas de un camino
   * i Inicio
   * j Fin del camino
   * regresa el camino mas corto
   */
  public List<Integer> shortestPath(int i, int j){
    if(current[getIndex(i,j)] == Double.POSITIVE_INFINITY){
      return null;
    }
    else{
      List<Integer> ans = new ArrayList<Integer>();
      ans.add(Integer.valueOf(i));
      shortestPathHelper(i,j,ans);
      return ans;
    }
  }
  /*
   * Metodo auxiliar
   */
  public void shortestPathHelper(int i, int j, List<Integer> partialPath){
    int index = getIndex(i,j);
    if(this.maxIndex[index] < 0){
      partialPath.add(Integer.valueOf(j));
    }
    else{
      shortestPathHelper(i,this.maxIndex[index],partialPath);
      shortestPathHelper(this.maxIndex[index],j,partialPath);
    }
  }

  /*Ejecuta el algoritmo de Floyd Warshall*/
  private class FloydJob implements Callable<Boolean>{

    private final int lo;
    private final int hi;
    private final int k;

    public FloydJob(int lo, int hi, int k){
      this.lo = lo;
      this.hi = hi;
      this.k = k;
    }

    /*Decide cual camino es el correcto*/
    public Boolean call() throws Exception {
      for(int index = lo; index < hi; index++){
        int i = getI(index);
        int j = getJ(index);
        double alternatePathValue = current[getIndex(i,k)]
                                      + current[getIndex(k,j)];

        if(alternatePathValue < current[index]){
          next[index] = alternatePathValue;
          maxIndex[index] = k;
        }
        else{
          next[index] = current[index];
        }
      }
      return true;
    }
  }
}
