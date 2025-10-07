import java.util.Random;

public class DownloadArquivos {

    public static class Download implements Runnable{
        private Random random = new Random();

        @Override
        public void run() {
            int progress = 0;
    
            while(progress < 100){
                 try {
                    Thread.sleep(random.nextInt(100, 2000));
                    progress = Math.min(progress + random.nextInt(1, 5), 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    
    
                System.out.println(Thread.currentThread().getName() + " : %" + progress);
            }
        }


    }

    public static void main(String[] args) throws InterruptedException {

        Thread arquivo1 = new Thread(new Download(), "Arquivo 1");
        Thread arquivo2 = new Thread(new Download(), "Arquivo 2");
        Thread arquivo3 = new Thread(new Download(), "Arquivo 3");

        arquivo1.start();
        arquivo2.start();
        arquivo3.start();

        arquivo1.join();
        arquivo2.join();
        arquivo3.join();
    }
}
