package concorrencia.blackFridayEsqueleto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SistemaProcessamento {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE PROCESSAMENTO DE PEDIDOS  ║");
        System.out.println("╚════════════════════════════════════════╝\n");
        
        // TODO: Criar BlockingQueue (PriorityBlockingQueue com capacidade 50)
        BlockingQueue<Pedido> fila = new PriorityBlockingQueue<>(50);
        
        // TODO: Criar GerenciadorEstoque
        GerenciadorEstoque estoque = new GerenciadorEstoque();
        
        // TODO: Criar GerenciadorEstatisticas
        GerenciadorEstatisticas stats = new GerenciadorEstatisticas();
        
        // TODO: Criar e iniciar Monitor
        Monitor monitor = new Monitor(fila, stats);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        // TODO: Criar ExecutorService para produtores (3 threads)
        ExecutorService produtores = Executors.newFixedThreadPool(3);
        
        // TODO: Criar 3 produtores (API, Web, Mobile) - cada um gera 20 pedidos
        
        produtores.execute(new Produtor(fila, "API", 20, stats));
        produtores.execute(new Produtor(fila, "WEB", 20, stats));
        produtores.execute(new Produtor(fila, "MOBILE", 20, stats));

        // TODO: Criar ExecutorService para consumidores (5 threads)
        ExecutorService consumidores = Executors.newFixedThreadPool(5);
        
        // TODO: Criar 5 consumidores
        
        consumidores.execute(new Consumidor(0, fila, estoque, stats));
        consumidores.execute(new Consumidor(1, fila, estoque, stats));
        consumidores.execute(new Consumidor(2, fila, estoque, stats));
        consumidores.execute(new Consumidor(3, fila, estoque, stats));
        consumidores.execute(new Consumidor(4, fila, estoque, stats));

        // TODO: Aguardar produtores finalizarem
        produtores.shutdown();

        // TODO: Aguardar consumidores finalizarem
        consumidores.shutdown();
        // TODO: Parar monitor
        
        monitor.parar();
        // TODO: Exibir relatório final
        
        stats.exibirRelatorioFinal();
        // TODO: Exibir estoque final
        
        estoque.exibirEstoque();

        System.out.println("\nSistema finalizado com sucesso!");
    }
}