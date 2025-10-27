package exercicioGemini4;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    
    // Variável atômica para contar o total de relatórios a serem gerados (Lotes)
    public static final int TOTAL_LOTES = 5;
    public static final AtomicInteger relatoriosGerados = new AtomicInteger(0);

    public static void main(String[] args) {
        
        // Fila 1: Coleta -> Transformação
        LinkedBlockingQueue<DadoBruto> filaColetaTransformacao = new LinkedBlockingQueue<>(100);
        // Fila 2: Transformação -> Relatório
        // Deve armazenar 10 itens por lote. Usamos 100 para ter buffer.
        LinkedBlockingQueue<String> filaTransformacaoRelatorio = new LinkedBlockingQueue<>(100);

        // O GeradorRelatorio é a ação a ser executada quando a barreira for alcançada.
        // A barreira será liberada por 1 thread: o GerenciadorLote.
        // Inicializamos a barreira com 1 participante e a Ação do Relatório.
        GeradorRelatorio geradorRelatorio = new GeradorRelatorio(filaTransformacaoRelatorio, relatoriosGerados, TOTAL_LOTES);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(1, geradorRelatorio);

        // Thread pool para as tarefas
        ExecutorService executorColetor = Executors.newSingleThreadExecutor();
        ExecutorService executorTransformador = Executors.newFixedThreadPool(3);
        ExecutorService executorGerenciador = Executors.newSingleThreadExecutor(); // Para o Gerenciador de Lote

        // 1. Coletor (Produtor)
        ColetorDados coletorDados = new ColetorDados(filaColetaTransformacao, TOTAL_LOTES * 10); // 5 lotes * 10 dados = 50 dados
        executorColetor.execute(coletorDados);

        // 2. Transformadores (Consumidores/Produtores)
        for (int i = 0; i < 3; i++) {
            Transformador t = new Transformador(filaColetaTransformacao, filaTransformacaoRelatorio);
            executorTransformador.execute(t);
        }

        // 3. Gerenciador de Lote (Sincronização e Disparo do Relatório)
        GerenciadorLote gerenciadorLote = new GerenciadorLote(filaTransformacaoRelatorio, cyclicBarrier, TOTAL_LOTES);
        executorGerenciador.execute(gerenciadorLote);

        // --- ENCERRAMENTO SEGURO ---
        
        // Espera até que o número total de relatórios/lotes seja atingido
        try {
            while (relatoriosGerados.get() < TOTAL_LOTES) {
                Thread.sleep(100); // Evita "busy waiting" total, mas a melhor solução é usar um Latch
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 1. Encerra os Executors
        executorColetor.shutdownNow();
        executorTransformador.shutdownNow();
        executorGerenciador.shutdownNow();
        
        // 2. Espera a finalização (opcional)
        try {
            if (!executorTransformador.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Algum transformador não encerrou a tempo.");
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupção durante o shutdown.");
        }
        
        System.out.println("\n--- PIPELINE ENCERRADO ---");
    }
}