package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceLeilao extends Remote {
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente) throws RemoteException;
    public double consultarMaiorLance() throws RemoteException;
    public String consultarHistorico() throws RemoteException;
    public boolean ofertarLance(double lance) throws RemoteException;
    public void sairDoLeilao() throws RemoteException;
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente, InterfaceCliente cliente) throws RemoteException;
}