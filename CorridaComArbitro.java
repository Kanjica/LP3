import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class CorridaComArbitro{
    public static void main(String[] args){
        
        CountDownLatch largada = new CountDownLatch(1);
        AtomicBoolean vencedorDefinido = new AtomicBoolean(false);

        Runnable corrida = () -> {
            try{
                largada.await();
                
                for(int i = 1; i <= 100; i++){
                    if(i % 10 == 0) {
                        System.out.println("O Corredor " + Thread.currentThread().getName() 
                                + " correu " + i + " metros");
                    }
                    Thread.sleep(new Random().nextInt(100, 500));
                }

                if(vencedorDefinido.compareAndSet(false, true)){
                    System.out.println("O Corredor " + Thread.currentThread().getName() + " venceu a corrida!");
                }
                else {
                    System.out.println("O Corredor " + Thread.currentThread().getName() + " terminou a corrida.");
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        };

        Thread corredor1 = new Thread(corrida, "Ha Dowan");
        Thread corredor2 = new Thread(corrida, "Liang Chen");
        Thread corredor3 = new Thread(corrida, "Zhang Wei");
        Thread corredor4 = new Thread(corrida, "Wang Fang");

        corredor1.start();
        corredor2.start();
        corredor3.start();
        corredor4.start();

        try{
            System.out.println("Preparar...");
            Thread.sleep(1000);
            System.out.println("Apontar...");
            Thread.sleep(1000);
            System.out.println("Já!");
            largada.countDown(); 
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
