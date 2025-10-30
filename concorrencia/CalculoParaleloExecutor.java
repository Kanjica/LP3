import java.util.concurrent.*;
import java.util.Scanner;

public class CalculoParaleloExecutor {

    public static class SomaParcial implements Callable<Long>{
        private final int inicio, fim;

        public SomaParcial(int inicio, int fim){
            this.inicio = inicio;
            this.fim = fim;
        }

        @Override
        public Long call(){
            long soma = 0;
            for (int i = inicio; i <= fim; i++) {
                soma += i;
            }
            System.out.println("Soma parcial [" + inicio + " - " + fim + "] = " + soma);
            return soma;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Fala um número aí, men: ");
        int numero = scanner.nextInt();
        scanner.close();

        int range = numero / 4;

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<Long> f1 = executor.submit(new SomaParcial(1, range));
        Future<Long> f2 = executor.submit(new SomaParcial(range + 1, range * 2));
        Future<Long> f3 = executor.submit(new SomaParcial(range * 2 + 1, range * 3));
        Future<Long> f4 = executor.submit(new SomaParcial(range * 3 + 1, numero));

        long resultado = f1.get() + f2.get() + f3.get() + f4.get();

        System.out.println("Resultado final = " + resultado);

        executor.shutdown();
    }
}
