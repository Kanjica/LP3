package Estacionamento;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Estacionamento {

    public static void main(String[] args){
        Semaforos semaforos = new Semaforos(new Semaphore(2), new Semaphore(5), 
                            new Semaphore(1), new Semaphore(1));

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for(int i=0; i<20; i++){
            double probabilidade = Math.random();

            executor.execute(new Veiculo(("Carro " + i), 
                                            probabilidade<=0.30? false : true, 
                                            semaforos));

        }


    }
}