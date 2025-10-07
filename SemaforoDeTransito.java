public class SemaforoDeTransito {

    public enum Cor {
        VERDE(5000), 
        AMARELO(2000), 
        VERMELHO(5000);

        private final int tempo;

        Cor(int tempo) {
            this.tempo = tempo;
        }

        public int getTempo() {
            return tempo;
        }
    }

    static class Semaforo implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("LUZ " + Cor.VERDE.name() + " ACESA!");
                    Thread.sleep(Cor.VERDE.getTempo());
                    
                    System.out.println("LUZ " + Cor.AMARELO.name() + " ACESA!");
                    Thread.sleep(Cor.AMARELO.getTempo());
                    
                    System.out.println("LUZ " + Cor.VERMELHO.name() + " ACESA!");
                    Thread.sleep(Cor.VERMELHO.getTempo());

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); 
                    System.out.println("Simulação de semáforo interrompida.");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread semaforo = new Thread(new Semaforo());
        semaforo.start();
        
        System.out.println("Simulação do semáforo iniciada na thread: " + semaforo.getName());
    }
}