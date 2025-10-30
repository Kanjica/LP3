package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 5000);
            InterfaceLeilao sistema = (InterfaceLeilao) registro.lookup("SistemaLeilao");

            Scanner sc = new Scanner(System.in);
            System.out.print("Digite seu nome: ");
            String nome = sc.nextLine();

            InterfaceCliente cliente = new ClienteImpl(nome);

            RespostaNotificacao resposta = sistema.registrarReceberNotificacao(nome, cliente);
            System.out.println(resposta.getMensagem());

            while (true) {
                System.out.println("\nDigite um comando (lance <valor> / sair): ");
                String comando = sc.nextLine();

                if (comando.startsWith("lance")) {
                    double valor = Double.parseDouble(comando.split(" ")[1]);
                    sistema.ofertarLance(valor);
                } else if (comando.equalsIgnoreCase("sair")) {
                    System.out.println("Saindo do leilão...");
                    break;
                }
            }

            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
