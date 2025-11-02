package rmi.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceChat extends Remote{
    public void conectar(InterfaceCliente cliente) throws RemoteException;
    public void enviarMensagem(InterfaceCliente remetente, String mensagem) throws RemoteException;
    public void desconectar(InterfaceCliente cliente) throws RemoteException;
    public void visualizarHistorico(InterfaceCliente cliente) throws RemoteException;
}
