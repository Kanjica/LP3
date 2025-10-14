package blackFridayEsqueleto;

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
        Lock lock = readWriteLock.readLock();

        lock.lock();
        try {
            Integer quantidade = estoque.get(produto);
            return quantidade == null? 0: quantidade;
        } finally {
            lock.unlock();
        }

    }
    
    public boolean reservarEstoque(String produto, int quantidade) {
        Lock lock = readWriteLock.writeLock();

        // 1. Adquirir write lock
        lock.lock();

        try {
            // 2. Verificar se tem estoque suficiente
            if(consultarEstoque(produto)>=quantidade){
                // 3. Decrementar estoque
                estoque.put(produto, quantidade);
                return true;
            }
            return false;
            
        } finally {
            // 4. Liberar lock
            lock.unlock();
        }
    }
    
    public void devolverEstoque(String produto, int quantidade) {
        Lock lock = readWriteLock.writeLock();

        lock.lock();

        try {
            if(consultarEstoque(produto)>=quantidade){
                // 3. Decrementar estoque
                estoque.put(produto, quantidade);
            }
        } finally {
            lock.unlock();
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