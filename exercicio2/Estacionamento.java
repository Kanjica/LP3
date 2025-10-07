package exercicio2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class Estacionamento {

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        Semaforos semaforos = new Semaforos(new Semaphore(2), new Semaphore(5), 
                            new Semaphore(1), new Semaphore(1));

        ExecutorService executor = Executors.newFixedThreadPool(4);
        int veiculosQueNaoEstacionaram = 0;
        int veiculosQueEstacionaram = 0;

        for(int i=0; i<20; i++){
            double probabilidade = Math.random();

            Future<Boolean> estacionou = executor.submit(new Veiculo(("Carro " + i), 
                                            probabilidade<=0.30? false : true, 
                                            semaforos));

            if(!estacionou.get()){veiculosQueNaoEstacionaram ++;}
            else{veiculosQueEstacionaram++;}

            //System.out.println("Veiculos que estacionaram: "+veiculosQueEstacionaram);
            //System.out.println("Veiculos que nao estacionaram: "+veiculosQueNaoEstacionaram);
        }


    }
}