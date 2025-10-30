package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteImpl extends UnicastRemoteObject implements InterfaceCliente {

    private String nome;

    protected ClienteImpl(String nome) throws RemoteException {
        super();
        this.nome = nome;
    }

    @Override
    public void receberNotificacao(String mensagem) throws RemoteException {
        System.out.println("Notificação recebida: " + mensagem);
    }

    public String getNome() {
        return nome;
    }
}
