package blackFridayEsqueleto;

import java.util.Random;
import java.util.concurrent.*;

class Consumidor implements Runnable {
    private final BlockingQueue<Pedido> fila;
    private final GerenciadorEstoque estoque;
    private final GerenciadorEstatisticas stats;
    private final int id;
    private final Random random = new Random();
    
    public Consumidor(int id, BlockingQueue<Pedido> fila, GerenciadorEstoque estoque,
                     GerenciadorEstatisticas stats) {
        this.id = id;
        this.fila = fila;
        this.estoque = estoque;
        this.stats = stats;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                // TODO: Remover pedido da fila com timeout (poll com 5 segundos)
                Pedido pedido = fila.poll(5, TimeUnit.SECONDS);

                // Se null, significa que não há mais pedidos, pode encerrar
                
                if(pedido==null) break;

                // TODO: Processar pedido
                processarPedido(pedido);

                // TODO: Se null, break do loop
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally { 
            System.out.println("[Consumidor-" + id + "] Finalizou processamento");
        }
    }
    
    private void processarPedido(Pedido pedido) throws InterruptedException {
        // TODO: Implementar processamento
        // 1. Verificar estoque
        String produto = pedido.getProduto();
        int quantidade = pedido.getQuantidade();

        // 2. Reservar estoque
        boolean reservou = estoque.reservarEstoque(produto, quantidade);

        // 3. Simular processamento (100-300ms)
        Thread.sleep(new Random().nextInt(100,300));
        
        // 4. Atualizar estatísticas
        if(reservou){
            stats.registrarPedidoProcessado();
            System.out.println("Pedido reservado");
        }
        else{
            stats.registrarPedidoRejeitado();
            System.out.println("Pedido não reservado");
        }
    }
}