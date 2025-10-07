package ContaBancaria;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        ContaBancaria contaBancaria = new ContaBancaria(1000);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                double valorSaque = random.nextDouble(10, 30);
                try {
                    contaBancaria.sacar(valorSaque);
                    Thread.sleep(500); 
                    System.out.println(Thread.currentThread().getName()
                            + " sacou: R$ " + String.format("%.2f", valorSaque)
                            + " | Saldo atual: R$ " + String.format("%.2f", contaBancaria.getSaldo()));
                } catch (SaldoInsuficienteException e) {
                    System.out.println(Thread.currentThread().getName()
                            + " tentou sacar R$ " + String.format("%.2f", valorSaque)
                            + " mas não tinha saldo suficiente!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.err.println("Tempo limite atingido, encerrando executor!");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("\nSaldo final da conta: R$ " + String.format("%.2f", contaBancaria.getSaldo()));
    }
}
