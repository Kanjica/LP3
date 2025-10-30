package exercicio1;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public final class ServerInitializer {
    
    private static CountDownLatch latch;
    
    public ServerInitializer(){
        latch = new CountDownLatch(4);
    }

    public void startServer() throws IOException{
        System.out.println("Servidor Principal Online: Pronto para aceitar conexões (Socket.bind())");
    }

    public void waitForInitialization() throws InterruptedException{
        System.out.println("Esperando Inicialização dos Modulos");
        latch.await();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}