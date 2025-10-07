package Estacionamento;

import java.util.concurrent.Semaphore;

public record Semaforos(Semaphore vagaPrioritaria, 
                        Semaphore vagaRegular,
                        Semaphore portaoEntrada,
                        Semaphore portaoSaida) {
}
