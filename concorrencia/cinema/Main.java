package cinema;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
    
        SalaCinema salaCinema = new SalaCinema();

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 22; i++) {
            executor.execute(new Cliente("Cliente " + i, salaCinema));
        }
        executor.shutdown();

        if(!executor.awaitTermination(5, TimeUnit.SECONDS)){
            executor.shutdownNow();
        }
    }
}
