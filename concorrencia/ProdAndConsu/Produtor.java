package ProdAndConsu;

import java.util.Random;
import java.util.concurrent.BlockingDeque;

public final class Produtor implements Runnable{
    
    private BlockingDeque<Integer> deque;

    public Produtor(BlockingDeque<Integer> deque){
        this.deque = deque;
    }

    @Override
    public void run() {
        int n = new Random().nextInt();

        try {
            this.deque.put(n);
            System.out.println("inserimo o " + n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
