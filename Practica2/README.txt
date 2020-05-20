## Práctica 2
Integrantes:
Alfaro Jimenez Juan Adolfo
Apipilhuasco Rosas Orlando Alain
Salas Martinez Mauricio Javier





    • ¿Qué es un proceso? 

	Es un cambio de estado de la memoria por la accion del procesador

    • ¿Qué es la sección crítica de un proceso?

	porción de código en la que se accede a un recurso compartido (estructura de 		datos o dispositivo) que no debe ser accedido por más de un proceso o hilo en 		ejecución.

    • ¿Qué es el problema libre de hambruna?

	la hambruna es cuando un proceso se le niegan los recursos necesarios para procesar de forma perpetua, es causada por errores de calendarizacion o problemas de exclusion mutua dentro del algoritmo cuando la hambruna no puede pasar se le llama libre de hambruna.

    • ¿Qué es el problema de abrazos mortales?

	Es un Estado de espera de todos los procesos por un evento que no sucederá se puede solucionar con ciertas condiciones como la exclusion mutua, retension y espera,condicion de no expropiacion, condicion de espera circular.

    • Haz un TDA de Hilos y un TDA de Semáforos de 4 puntos

    • TDA Hilos.
	Permiten implementar el que determinado trozo de codigo, pueda ser ejecutado concurrentemente por diferentes contextos de programacion llamados hilos.

	Thread(String nombre) . Constructor al que solo le pongamos un id para identificarlo 
	Thread(). Constructor sin parametros, Crea hilos de manera automatica.
	start(). Inicia la ejecucion de un hilo.
	run(). Controla la ejecucion de un hilo.
	sleep(tiempo) Causa que el hilo se duerma un tiempo determinado.
	interrupt() Interrumpe la ejecucion de un hilo
	join() Espera a que un hilo especifico muera antes de continuar.
	yield() El hilo cede la ejecucion a otros hilos.

    • TDA Semaforos.

	Constituye el metodo clasico para restringir o permitir el acceso a recursos compartidos en un entorno de multiprocesamiento.

	Wait(P) Si el valor del semaforo es no nulo decrementa en uno el valor del semaforo, Si es nulo el thread que lo ejecuta se suspende y encola en la lista de procesos en espera del semaforo.

	Signal(p) Si hay un proceso en la lista de procesos del semaforo. activa uno de ellos para que ejecute la sentencia que sigue al wait que lo suspendio. Si no hay procesos en espera  en la lista incrementa en 1 el valor del semaforo.