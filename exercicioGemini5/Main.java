package exercicioGemini5;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // Record para organizar os Semáforos
    private record Semaphores(Semaphore ePadrao, Semaphore eAD) {}
    // ePadrao (3 vagas), eAD (1 vaga)
    public static Semaphores semaphores = new Semaphores(new Semaphore(3), new Semaphore(1));

    // Record para as Estatísticas (com AtomicInteger para segurança de thread)
    public record Stats(AtomicInteger alunosQueEntraram, AtomicInteger eAD, AtomicInteger contadorDesistencias){}
    
    public static Stats stats = new Stats(new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0));
    
    public static class Aluno implements Runnable {

        private final String nome;
        private final Semaphores semaphores;
        private final Stats stats;
        private static final Random random = new Random();

        public Aluno(String nome, Semaphores semaphores, Stats stats){
            this.nome = nome;
            this.semaphores = semaphores;
            this.stats = stats;
        }

        @Override
        public void run() {
            stats.alunosQueEntraram.getAndIncrement();
            
            try {
                // Tenta 1: Prioritária (eAD) com timeout
                if(semaphores.eAD.tryAcquire(500, TimeUnit.MILLISECONDS)){
                    try {
                        System.out.println("Aluno: "+ this.nome + " pegou PRIORITÁRIA 🌟");
                        // Simula uso do recurso
                        TimeUnit.MILLISECONDS.sleep(random.nextInt(1000, 3000));
                        stats.eAD.getAndIncrement();
                    } finally {
                        // GARANTE A LIBERAÇÃO DA PRIORITÁRIA
                        semaphores.eAD.release();
                    }
                }
                // Tenta 2: Padrão
                else{
                    // Tenta o semáforo padrão sem timeout
                    if(semaphores.ePadrao.tryAcquire()){
                        try {
                            System.out.println("Aluno: "+ this.nome + " pegou NORMAL 👍");
                            // Simula uso do recurso
                            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000, 3000));
                        } finally {
                            // GARANTE A LIBERAÇÃO DA PADRÃO
                            semaphores.ePadrao.release();
                        }
                    }
                    // Tenta 3: Desiste
                    else{
                        System.out.println("Aluno: "+ this.nome + " kitou 🚪");
                        stats.contadorDesistencias.getAndIncrement();
                    }
                }
            } catch (InterruptedException e) {
                // Trata a interrupção da thread, se ocorrer durante tryAcquire ou sleep
                System.err.println("Aluno " + nome + " interrompido.");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Usamos FixedThreadPool se o número de alunos for grande, mas CachedThreadPool funciona.
        ExecutorService executorService = Executors.newCachedThreadPool(); 

        for (int index = 0; index < 30; index++) {
            executorService.execute(new Aluno("Bananinsol" + index, semaphores, stats));
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100, 200));
        }

        // --- ENCERRAMENTO SEGURO ---
        executorService.shutdown();

        // Espera a conclusão por até 10 segundos
        if(!executorService.awaitTermination(10, TimeUnit.SECONDS)){
            System.out.println("\nTIMEOUT! Forçando o encerramento dos alunos remanescentes...");
            executorService.shutdownNow(); // Força o encerramento
        }

        showStats(stats);
    }

    public static void showStats(Stats stats){
        System.out.println("\n--- ESTATÍSTICAS FINAIS ---");
        System.out.println("Total de alunos que tentaram entrar: "+stats.alunosQueEntraram.get());
        System.out.println("Alunos que conseguiram vaga PRIORITÁRIA: " + stats.eAD.get());
        System.out.println("Alunos que desistiram (Kitou): " + stats.contadorDesistencias.get());
        
        int totalAcessos = stats.eAD.get() + (stats.alunosQueEntraram.get() - stats.contadorDesistencias.get() - stats.eAD.get());
        System.out.println("Total de acessos (Prioritária + Padrão): " + totalAcessos);
    }
}