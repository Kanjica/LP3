package exercicio2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class Estacionamento {

    public static void main(String[] args) throws InterruptedException, ExecutionException{
        Semaforos semaforos = new Semaforos(new Semaphore(2), new Semaphore(5), 
                            new Semaphore(1), new Semaphore(1));

        List<Integer> tempoEstacionado = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        int veiculosQueNaoEstacionaram = 0;
        int veiculosQueEstacionaram = 0;

        List<Future<veiculoDTO>> resultadosPendentes = new ArrayList<>(); 

        for(int i=0; i<20; i++){
            double probabilidade = Math.random();
            Future<veiculoDTO> veiculoDados = executor.submit(new Veiculo(("Carro " + i), 
                                            probabilidade<=0.30? false : true, 
                                            semaforos));
            resultadosPendentes.add(veiculoDados); 
        }

        for (Future<veiculoDTO> future : resultadosPendentes) {
            veiculoDTO dados = future.get(); 
            
            if(!dados.estacionou()){
                veiculosQueNaoEstacionaram++;
            } 
            else{
                veiculosQueEstacionaram++;
                tempoEstacionado.add(dados.tempoEstacionado());
            }
        }

        executor.shutdown(); 

        double tempoMedio = tempoEstacionado.stream()
                                .mapToInt(Integer::intValue)
                                .sum();

        System.out.println("Tempo médio: " + tempoMedio/tempoEstacionado.size());
    }
}