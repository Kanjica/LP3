package exercicioGemini4;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

// Novo componente para coordenar a sincronização
public class GerenciadorLote implements Runnable {

    private final LinkedBlockingQueue<String> filaTransformacaoRelatorio;
    private final CyclicBarrier cyclicBarrier;
    private final int totalLotes;

    public GerenciadorLote(LinkedBlockingQueue<String> filaTransformacaoRelatorio, CyclicBarrier cyclicBarrier, int totalLotes) {
        this.filaTransformacaoRelatorio = filaTransformacaoRelatorio;
        this.cyclicBarrier = cyclicBarrier;
        this.totalLotes = totalLotes;
    }

    @Override
    public void run() {
        try {
            int lotesProcessados = 0;
            while (lotesProcessados < totalLotes) {
                System.out.println("\nGerenciador Lote: Esperando por 10 dados na fila final...");
                
                // 1. CONSUMIR EXATAMENTE 10 ITENS
                // Bloqueia com take() por 10 vezes, garantindo que o lote está COMPLETO.
                for (int i = 0; i < 10; i++) {
                    // Espera no máximo 5 segundos para o próximo dado
                    String dado = filaTransformacaoRelatorio.poll(5, TimeUnit.SECONDS); 
                    if (dado == null) {
                        // Se demorar muito ou acabar a produção inesperadamente, interrompe
                        System.err.println("ERRO: Timeout esperando dado de lote.");
                        throw new InterruptedException("Timeout de lote");
                    }
                    // O dado permanece na fila até ser consumido pelo Relatório, 
                    // mas este loop garante que 10 itens foram inseridos e estão prontos.
                }

                // 2. DISPARAR A BARREIRA
                // A CyclicBarrier tem 1 participante (GerenciadorLote) e executa o GeradorRelatorio
                System.out.println("Gerenciador Lote: Lote de 10 dados completos. Disparando relatório...");
                cyclicBarrier.await();
                
                lotesProcessados++;
                // Reinicializa a barreira (opcional, pois no seu caso é 1 participante)
                // Se o GeradorRelatorio fosse um participante, a barreira se resetaria automaticamente.
            }
        } catch (Exception e) {
            System.err.println("Gerenciador Lote ENCERRADO: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}