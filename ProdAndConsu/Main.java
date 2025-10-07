package ProdAndConsu;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) {
        
        BlockingDeque<Integer> deque = new LinkedBlockingDeque<>();
    
        Produtor prod = new Produtor(deque);
        Consumidor consu = new Consumidor(deque);
    
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    
        scheduler.scheduleAtFixedRate(prod, 0, 2, TimeUnit.SECONDS);

        scheduler.execute(consu);

        try {
            Thread.sleep(10000);
            scheduler.shutdownNow();
        } catch (Exception e) {
        }
    }
}