import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RelogioDigital {
    public static void main(String[] args) {
        /* 
        Thread relogioDigital = new Thread(() -> {
            for(int i=0; i<10; i++){
                System.out.print("\r" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        
        relogioDigital.start();
        */
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            System.out.print("\r" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }, 0, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduler.shutdown();
    }
}
