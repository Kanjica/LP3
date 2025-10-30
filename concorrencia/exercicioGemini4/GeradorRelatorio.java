package exercicioGemini4;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GeradorRelatorio implements Runnable {

    private final LinkedBlockingQueue<String> filaTransformacaoRelatorio;
    private final AtomicInteger relatoriosGerados;
    private final int totalLotes;

    public GeradorRelatorio(LinkedBlockingQueue<String> filaTransformacaoRelatorio, AtomicInteger relatoriosGerados, int totalLotes) {
        this.filaTransformacaoRelatorio = filaTransformacaoRelatorio;
        this.relatoriosGerados = relatoriosGerados;
        this.totalLotes = totalLotes;
    }

    // Chamado pelo CyclicBarrier quando o lote está pronto
    @Override
    public void run() {
        int loteAtual = relatoriosGerados.get() + 1;
        
        System.out.println("\n--- INICIANDO RELATÓRIO DO LOTE " + loteAtual + " ---");
        
        try {
            int cont = 0;
            StringBuilder sbDados = new StringBuilder("Relatório Lote " + loteAtual + " (Total " + 10 + " itens):\n");
            
            // Consome 10 itens da fila (deve ser 10, pois o GerenciadorLote já garantiu a existência)
            for (int i = 0; i < 10; i++) {
                // take() para garantir que pegamos o dado, mas poll() funciona se o GerenciadorLote garantir.
                String dadoTransformado = filaTransformacaoRelatorio.take(); 
                sbDados.append("  ").append(dadoTransformado).append("\n");
                cont++;
            }
            
            System.out.println(sbDados.toString());
            System.out.println("--- RELATÓRIO DO LOTE " + loteAtual + " CONCLUÍDO. (" + cont + " itens processados) ---");

            // Incrementa o contador de relatórios
            relatoriosGerados.incrementAndGet();

        } catch (InterruptedException e) {
            System.err.println("Relatório interrompido.");
            Thread.currentThread().interrupt();
        }
    }
}