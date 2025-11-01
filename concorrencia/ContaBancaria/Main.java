package concorrencia.ContaBancaria;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        // ContaBancaria contaBancaria = new ContaBancaria(1000);
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Random random = new Random();

        // for (int i = 0; i < 100;i++) {
        //     executor.submit(() -> {
        //         double valorSaque = random.nextDouble(10, 30);
        //         try {
        //             ResultadoSaque resultado = contaBancaria.sacarComInformacao(valorSaque);
        //             Thread.sleep(500);
                    
        //             if (resultado.sucesso()) {
        //                 System.out.println(Thread.currentThread().getName()
        //                     + " sacou: R$ " + String.format("%.2f", resultado.valorSaque())
        //                     + " | Saldo anterior: R$ " + String.format("%.2f", resultado.saldoAnterior())
        //                     + " | Saldo atual: R$ " + String.format("%.2f", resultado.novoSaldo()));
        //             } else {
        //                 System.out.println(Thread.currentThread().getName()
        //                     + " tentou sacar R$ " + String.format("%.2f", resultado.valorSaque())
        //                     + " | Saldo insuficiente: R$ " + String.format("%.2f", resultado.saldoAnterior()));
        //             }
        //         } catch (InterruptedException e) {
        //             Thread.currentThread().interrupt();
        //         }
        //     });
        // }

        // executor.shutdown();
        // try {
        //     if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
        //         System.err.println("Tempo limite atingido, encerrando executor!");
        //         executor.shutdownNow();
        //     }
        // } catch (InterruptedException e) {
        //     executor.shutdownNow();
        // }
 
        List<ContaBancaria> contas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            contas.add(new ContaBancaria("Conta " + i, 5000));
        }

        contas.forEach((conta) -> {
            for (int i = 0; i < 3; i++) {
                conta.transferir(contas.get(random.nextInt(contas.size())),
                random.nextDouble(4400));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        contas.forEach((conta) -> {
            System.out.println("Saldo final da conta " + conta.getNome() + ": " + String.format("%.2f",conta.getSaldo()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        // System.out.println("\nSaldo final da conta: R$ " + String.format("%.2f", contaBancaria.getSaldo()));
        // System.out.println("\nSaldo final da conta 2: R$ " + String.format("%.2f", contaBancaria2.getSaldo()));
    }
}
