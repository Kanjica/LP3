package concorrencia.exercicio1;

public class ModuleLoader implements Runnable{
    private final String module;
    private final int sleep; 
    private final ServerInitializer latch; 

    public ModuleLoader(String module, int sleep, ServerInitializer latch){
        this.module = module;
        this.sleep = sleep;
        this.latch = latch;
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void run() {
        System.out.println("#######Iniciando Módulo de " + module + " #######");
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            latch.getLatch().countDown();
            System.out.println("#######Terminando Módulo de " + module + " #######");
        }
    }
    
}
