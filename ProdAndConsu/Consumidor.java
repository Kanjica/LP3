package ProdAndConsu;

import java.util.concurrent.BlockingDeque;

public final class Consumidor implements Runnable{

    private BlockingDeque<Integer> deque;

    public Consumidor(BlockingDeque<Integer> deque){
        this.deque = deque;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try {
                System.out.println("peguemo o " + deque.take()+ "\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("a k bou c o q era dou ce");
            }  
        }
    }
    
}
