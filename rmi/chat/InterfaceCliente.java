package rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCliente extends Remote{
    public void receberMensagem(String nome, String mensagem) throws RemoteException;
    String getNome() throws RemoteException;

}
