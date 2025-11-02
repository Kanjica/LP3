package rmi.chat;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import rmi.leilao.ClienteImpl;

public class Cliente extends UnicastRemoteObject implements InterfaceCliente{

    private String nome;

    protected Cliente(String nome) throws RemoteException {
        super();
        this.nome = nome;
    }

    @Override
    public void receberMensagem(String remetente, String mensagem) throws RemoteException {
        System.out.println("["+ remetente + "] " + mensagem);
    }

    @Override
    public String getNome() {
        return nome;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost",5020);
        InterfaceChat chat = (InterfaceChat) registry.lookup("Chat");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();

        InterfaceCliente eu = new Cliente(nome);

        chat.conectar(eu);

        System.out.println("Para ver o histórico digite historico.");
        while (true) {
            String mensagem = sc.nextLine();

            if(mensagem.equalsIgnoreCase("historico")) chat.visualizarHistorico(eu);
            else chat.enviarMensagem(eu, mensagem);
        }
    }
    
}