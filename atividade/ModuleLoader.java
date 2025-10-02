public class ModuleLoader implements Runnable{
    private String module;
    private int sleep; 
    private ServerInitializer latch; 

    public ModuleLoader(String module, int sleep, ServerInitializer latch){
        this.module = module;
        this.sleep = sleep;
        this.latch = latch;
    }

    @Override
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
