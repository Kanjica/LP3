package exercicioGemini4;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Transformador implements Runnable {

    private final LinkedBlockingQueue<DadoBruto> filaColetaTransformacao;
    private final LinkedBlockingQueue<String> filaTransformacaoRelatorio;
    private static final Random random = new Random();

    public Transformador(LinkedBlockingQueue<DadoBruto> filaColetaTransformacao, LinkedBlockingQueue<String> filaTransformacaoRelatorio) {
        this.filaColetaTransformacao = filaColetaTransformacao;
        this.filaTransformacaoRelatorio = filaTransformacaoRelatorio;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // take() bloqueia e ESPERA por um dado (Correção do poll())
                DadoBruto db = filaColetaTransformacao.take();

                // Verifica o marcador de FIM para parar o pipeline
                if (db == ColetorDados.FIM_DADO) {
                    System.out.println(Thread.currentThread().getName() + ": Recebeu marcador de fim. Encerrando.");
                    // Devolve o marcador de FIM para que outros transformadores também parem
                    filaColetaTransformacao.put(ColetorDados.FIM_DADO); 
                    break;
                }

                // 1. Simula processamento (200-500 ms)
                TimeUnit.MILLISECONDS.sleep(200 + random.nextInt(300));

                // 2. Transforma (Produz String para a próxima fila)
                String dadosTransformados = String.format("Transformado [ID: %d | Payload Processado]", db.getId());

                // 3. put() na fila final (Bloqueia se a fila estiver cheia)
                filaTransformacaoRelatorio.put(dadosTransformados);

                System.out.println(Thread.currentThread().getName() + ": Processou dado " + db.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}