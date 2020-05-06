## Práctica 4

Apipilhuasco Rosas Orlando Alain 311067006
Salas Martínez Mauricio Javier   311014859
Juan Adolfo Alfaro Jiménez       311255357

¿Qué es Coste secuencial? 
Es un proceso lento ya que va una tarea tras otra y si una tarea se atrasa todo el trabajo debe esperar por tanto tiene un costo mas alto. Pero son mas faciles de entender y implementar. Por tanto el tiempo de ejecucion es mayor.
El algoritmo para multiplicar matrices tiene un tiempo de O(n^3)
El algoritmo FloyWarshall secuencial toma O(n^3)

¿Qué es Coste paralelo?

Se refiere a una medida de cuan efectiva se puede usar la computación paralela para resolver un problema particular.
Es optimo si su tiempo de ejecución multiplicado por el número de unidades de procesamiento involucradas en el cálculo es comparable con el tiempo de ejecución del mejor algorimo secuencual.Lo cual quiere decir que disminuye en comparacion al secuencial. 


Como la multiplicacion se hace solo de una fila y una columna, por hilo, el tiempo de ejecucion se vuelve en promedio O(n) 

El timepo de ejecucion que se obtiene al repartir las tareas para encontrar el camino mas corto en FloyWarshall es en promedio O(n) 

¿Qué es Speedup?
El speeduo esta definido como la relacion de ejecución en serie del mejor algoritmo secuencial para resolver un problema A en el tiempo que tarda el algoritmo paralelo en resolver el mismo problema en los procesadores P.

	S= Ts/Tp


¿Qué es Eficiencia? 

Son aquellas propiedades de los algoritmos que están relacionadas con la cantoidad de recursos utilizados por el algoritmo.Se analiza para determinar el uso de los recursos que realiza. Se busca reducir el uso de recursos. Muchos problemas no logran ser eficientes por lo que se elije una medida de eficiencia que se pone como prioridad.

