package exercicio1;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthService{
    public static void main(String args[]){

        try {
            ServerInitializer initializer = new ServerInitializer();
            ExecutorService executor = Executors.newFixedThreadPool(4);

            initializerModules(executor, initializer);
            initializer.waitForInitialization();
            initializer.startServer();

        } catch (IOException | InterruptedException e) {
            System.out.println("Deu erro pcr" + e.getMessage());
        } finally {

        }
    }

    public static void initializerModules(ExecutorService executor, ServerInitializer initializer){
        Random random = new Random();
        executor.execute(new ModuleLoader("Configuração", random.nextInt(5000, 7000), initializer));
        executor.execute(new ModuleLoader("Cache", random.nextInt(8000, 10000), initializer));
        executor.execute(new ModuleLoader("Chaves de Criptografia", random.nextInt(11000, 13000), initializer));
        executor.execute(new ModuleLoader("Conexão de Log", random.nextInt(3000, 5000), initializer));
    }
}