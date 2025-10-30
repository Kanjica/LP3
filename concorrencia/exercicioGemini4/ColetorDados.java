package exercicioGemini4;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ColetorDados implements Runnable {

    private final LinkedBlockingQueue<DadoBruto> filaColetaTransformacao;
    private final int totalDados;
    // Marcador de fim de produção (Sentinel) - Essencial para encerramento limpo
    public static final DadoBruto FIM_DADO = new DadoBruto(-1L, -1);

    public ColetorDados(LinkedBlockingQueue<DadoBruto> filaColetaTransformacao, int totalDados) {
        this.filaColetaTransformacao = filaColetaTransformacao;
        this.totalDados = totalDados;
    }

    @Override
    public void run() {
        long i = 0L;
        try {
            System.out.println("Coletor: Iniciando coleta de " + totalDados + " dados.");
            while (i < totalDados) {
                // put() bloqueia se a fila estiver cheia (thread-safe)
                filaColetaTransformacao.put(new DadoBruto(i, new Random().nextInt(100)));
                i++;

                // Simulação de tempo de coleta
                TimeUnit.MILLISECONDS.sleep(10);
            }
            
            // Sinaliza o fim da produção para todos os transformadores
            System.out.println("Coletor: Coleta finalizada. Inserindo marcadores de FIM.");
            for (int j = 0; j < 3; j++) {
                filaColetaTransformacao.put(FIM_DADO);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}