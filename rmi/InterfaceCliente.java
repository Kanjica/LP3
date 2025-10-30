package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface remota que o cliente implementará
public interface InterfaceCliente extends Remote {
    void receberNotificacao(String mensagem) throws RemoteException;
}
