public class PingPong {
    
    public static void main(String[] args) {
        
        Thread ping = new Thread(() -> {
            try {
                long inicio = System.currentTimeMillis();

                while ((System.currentTimeMillis()) - inicio < 10000) {
                    System.out.println("PING");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    
        Thread pong = new Thread(() -> {
            try {
                long inicio = System.currentTimeMillis();

                while ((System.currentTimeMillis()) - inicio < 10000) {
                    System.out.println("PONG");
                    Thread.sleep(700);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    
        ping.start();
        pong.start();
    }
}
