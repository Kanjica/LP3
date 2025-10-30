package set11;

public class Threads1 { 
    public static void main(String[] args) {
        MyRunnable r =  new MyRunnable();

        Thread t0 = new Thread(r);
        t0.start();
        
        Thread t1 = new Thread(r);
        t1.start();

        Thread t2 = new Thread(r);
        t2.start();

        Thread t3 = new Thread(r);
        t3.start();

        Thread t4 = new Thread(r);
        t4.start();

        Thread t5 = new Thread(r);
        t5.start();
        
        Thread t6 = new Thread(r);
        t6.start();

        Thread t7 = new Thread(r);
        t7.start();

        Thread t8 = new Thread(r);
        t8.start();

        Thread t9 = new Thread(r);
        t9.start();

        Thread t10 = new Thread(r);
        t10.start();


    }
}
