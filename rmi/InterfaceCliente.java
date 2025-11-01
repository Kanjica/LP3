package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCliente extends Remote {
    void receberNotificacao(String mensagem) throws RemoteException;
    String getNome() throws RemoteException;
    
}
