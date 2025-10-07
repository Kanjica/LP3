package Estacionamento;

import java.util.Random;

public class Veiculo implements Runnable{

    public String name;
    public boolean isPrioridade;
    public Semaforos semaforos;

    public Veiculo(String name, boolean isPrioridade, Semaforos semaforos){
        this.name = name;
        this.isPrioridade = isPrioridade;
        this.semaforos = semaforos;
    }
    @Override
    public void run() {
        Random random = new Random();
        try {
            semaforos.portaoEntrada().acquire();
            Thread.sleep(100);
            semaforos.portaoEntrada().release();

            if(isPrioridade){
                if(semaforos.vagaPrioritaria().tryAcquire()){
                    Thread.sleep(random.nextInt(1000, 5000));
                    semaforos.vagaPrioritaria().release();
                }
                else if(semaforos.vagaRegular().tryAcquire()){
                    Thread.sleep(random.nextInt(1000, 5000));
                    semaforos.vagaRegular().release();
                }
                else{
                    semaforos.portaoSaida().acquire();
                    Thread.sleep(100);
                    semaforos.portaoSaida().release();
                }
            }
            else{
                if(semaforos.vagaRegular().tryAcquire()){
                    Thread.sleep(random.nextInt(1000, 5000));
                    semaforos.vagaRegular().release();
                }
                else{
                    semaforos.portaoSaida().acquire();
                    Thread.sleep(100);
                    semaforos.portaoSaida().release();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
