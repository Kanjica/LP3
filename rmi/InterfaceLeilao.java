package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceLeilao extends Remote {
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente, InterfaceCliente cliente) throws RemoteException;
    public Lance consultarMaiorLance() throws RemoteException;
    public String consultarHistorico() throws RemoteException;
    public boolean ofertarLance(double lance, InterfaceCliente cliente) throws RemoteException;
    public void sairDoLeilao(InterfaceCliente cliente) throws RemoteException;
}