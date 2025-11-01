package concorrencia.blackFridayEsqueleto;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class GerenciadorEstoque {
    private final ConcurrentHashMap<String, Integer> estoque;
    private final ReadWriteLock readWriteLock;
    
    public GerenciadorEstoque() {
        this.estoque = new ConcurrentHashMap<>();
        this.readWriteLock = new ReentrantReadWriteLock();
        inicializarEstoque();
    }
    
    private void inicializarEstoque() {
        estoque.put("Notebook", 10);
        estoque.put("Mouse", 50);
        estoque.put("Teclado", 30);
        estoque.put("Monitor", 15);
        estoque.put("Headset", 25);
    }
    
    public int consultarEstoque(String produto) {
        // TODO: Implementar consulta com read lock
        
        try{
            readWriteLock.readLock().lock();

            Integer quantidade = estoque.get(produto);

            return quantidade != null? quantidade : 0;
        } finally{
            readWriteLock.readLock().unlock();
        }
    }
    
    public boolean reservarEstoque(String produto, int quantidade) {
        // TODO: Implementar reserva com write lock
        try{
            // 1. Adquirir write lock
            readWriteLock.writeLock().lock();

            // 2. Verificar se tem estoque suficiente
            int quantidadeEmEstoque = consultarEstoque(produto);

            if(quantidade >= quantidadeEmEstoque){
                // 3. Decrementar estoque
                estoque.put(produto, quantidade - quantidadeEmEstoque);
                return true;
            }
            return false;
        }
        finally{
            // 4. Liberar lock
            readWriteLock.writeLock().unlock();
        }
    }
    
    public void devolverEstoque(String produto, int quantidade) {
        try{
            readWriteLock.writeLock().lock();
            if(estoque.containsKey(produto)){
                estoque.put(produto, consultarEstoque(produto) + quantidade);
                System.out.println("Devolvido");
            }
            else{
                System.out.println("Não devolvido");
            }
        }finally{
            readWriteLock.writeLock().unlock();
        }

    }
    
    public void exibirEstoque() {
        readWriteLock.readLock().lock();
        try {
            System.out.println("\n=== ESTOQUE ATUAL ===");
            estoque.forEach((produto, qtd) -> 
                System.out.printf("%s: %d unidades\n", produto, qtd));
            System.out.println("====================\n");
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}