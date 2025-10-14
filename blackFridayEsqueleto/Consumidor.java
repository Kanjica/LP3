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
            
                Pedido pedido = fila.poll();

                if(!(pedido == null)){
                    //processando pedido
                    estoque.consultarEstoque(pedido.getProduto());
                }
                else{
                    break;
                }   
                
                // TODO: Processar pedido
                // TODO: Se null, break do loop
                Thread.sleep(1);
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
        // 2. Reservar estoque
        // 3. Simular processamento (100-300ms)
        // 4. Atualizar estatísticas
    }
}