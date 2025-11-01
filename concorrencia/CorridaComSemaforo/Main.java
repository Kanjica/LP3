package concorrencia.CorridaComSemaforo;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    
    public static void main(String[] args) {
        final int NUM_CARROS = 4;
        
        CountDownLatch largada = new CountDownLatch(1);
        CountDownLatch chegada = new CountDownLatch(NUM_CARROS);
        BlockingDeque<String> ordemChegada = new LinkedBlockingDeque<>();
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CARROS);
        Random random = new Random();

        for (int i = 0; i < NUM_CARROS; i++) {
            executor.submit(() -> {
                String nomeCarro = Thread.currentThread().getName();
                
                try {
                    largada.await(); 
                    
                    for (int j = 1; j <= 1000; j++) {
                        if (j % 100 == 0) {
                            System.out.println("O Carro " + nomeCarro + " andou " + j + " metros");
                        }
                        Thread.sleep(random.nextInt(1, 3));
                    }
                    
                    ordemChegada.add(nomeCarro);
                    System.out.println("O carro " + nomeCarro + " cruzou a linha de chegada.");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    chegada.countDown();
                }
            });
        }
        
        try {
            System.out.println("Preparar...");
            Thread.sleep(1000);
            System.out.println("A LARGADA!");
            largada.countDown(); 
            
            chegada.await(); 
            
            System.out.println("\n--- RESULTADO DA CORRIDA ---");
            System.out.println("Ordem de chegada:");
            
            int posicao = 1;
            while (!ordemChegada.isEmpty()) {
                System.out.println(posicao++ + "º lugar: " + ordemChegada.poll());
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    System.err.println("Tempo limite atingido, encerrando executor!");
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }
}