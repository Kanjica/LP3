package exercicio2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Veiculo implements Callable<veiculoDTO>{
    private static final Logger logger = Logger.getLogger(Veiculo.class.getName());

    public String name;
    public boolean isPrioridade;
    public Semaforos semaforos;
    public boolean estacionou;
    public int tempoEstacionado;

    public Veiculo(String name, boolean isPrioridade, Semaforos semaforos){
        this.name = name;
        this.isPrioridade = isPrioridade;
        this.semaforos = semaforos;
        this.estacionou = false;
        this.tempoEstacionado = 0;
    }

    @Override
    public veiculoDTO call() throws Exception{
        Random random = new Random();
        try{
            semaforos.portaoEntrada().acquire();
            logger.log(Level.INFO, "Veículo {0} passou pelo portão de entrada.", name);
            Thread.sleep(100);
            semaforos.portaoEntrada().release();

            if(isPrioridade){
                logger.log(Level.INFO, "Veículo {0} tentará estacionar na vaga prioritária.", name);
                if(semaforos.vagaPrioritaria().tryAcquire()){
                    estacionou = true;
                    logger.log(Level.INFO, "Veículo {0} estacionou na vaga prioritária.", name);
                    Thread.sleep(tempoEstacionado = random.nextInt(1000, 5000)); 
                    semaforos.vagaPrioritaria().release();
                }else{
                    logger.log(Level.WARNING, "Veículo {0} não conseguiu estacionar na vaga prioritária.", name);
                }
            }

            if(!estacionou){
                logger.log(Level.INFO, "Veículo {0} tentará estacionar na vaga regular.", name);
                if(semaforos.vagaRegular().tryAcquire()){
                    estacionou = true;
                    logger.log(Level.INFO, "Veículo {0} estacionou na vaga regular.", name);
                    Thread.sleep(tempoEstacionado = random.nextInt(1000, 5000)); 
                    semaforos.vagaRegular().release();
                }else{
                    logger.log(Level.WARNING, "Veículo {0} não conseguiu estacionar na vaga regular.", name);
                }
            }

            logger.log(Level.INFO, "Veículo {0} ficou estacionado por {1} segundos", new Object[]{name, tempoEstacionado});
            semaforos.portaoSaida().acquire();
            logger.log(Level.INFO, "Veículo {0} passou pelo portão de saída.", name);
            Thread.sleep(100);
            semaforos.portaoSaida().release();

            return new veiculoDTO(estacionou, tempoEstacionado);

        }catch (InterruptedException e){
            logger.log(Level.SEVERE, "O veiculo {0} foi interrompido: {1}", new Object[]{name, e.getMessage()});
            Thread.currentThread().interrupt(); 
        }

        return new veiculoDTO(false, 0);
    }
    
}
