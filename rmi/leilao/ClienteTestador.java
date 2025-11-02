package rmi.leilao;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClienteTestador {
    public static class ClienteRunnable implements Runnable{

        String nome;
        InterfaceLeilao sistema;
        public ClienteRunnable(String nome, InterfaceLeilao sistema){
            this.nome = nome;
            this.sistema = sistema;
        }  

        @Override
        public void run() {
            try {
                InterfaceCliente eu = new ClienteImpl(nome);
                System.out.println(sistema.registrarReceberNotificacao(nome, eu));
                Random random = new Random();
                while (true) {

                    int acao = new Random().nextInt(0, 100);

                    if(acao >= 0 && acao <= 90){
                        Double ultimoMaiorLance = sistema.consultarMaiorLance().lanceFeito();
                        sistema.ofertarLance(random.nextDouble(ultimoMaiorLance+0.1, (ultimoMaiorLance+random.nextDouble(5, 4000))),eu);
                    }
                    else if(acao <= 93){
                        sistema.consultarHistorico();
                    } 
                    else if(acao <= 96){
                        sistema.consultarMaiorLance();
                    }
                    else if (acao <=100){
                        sistema.sairDoLeilao(eu);
                    }
                    TimeUnit.MILLISECONDS.sleep(400);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("Paremo");
                Thread.interrupted();
            }
                
        }

    }
    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        
        Registry registro = LocateRegistry.getRegistry("localhost", 5000);
        InterfaceLeilao sistema = (InterfaceLeilao) registro.lookup("SistemaLeilao");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for(int i=0; i< 6; i++){
            executor.execute(new ClienteRunnable("Cliente" + i, sistema));
        }

        executor.shutdown();

        if(!executor.awaitTermination(20, TimeUnit.SECONDS)){executor.shutdownNow();}
        System.exit(0);
    }
}
