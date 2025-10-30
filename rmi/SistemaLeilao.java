package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class SistemaLeilao extends UnicastRemoteObject implements InterfaceLeilao {

    private List<InterfaceCliente> clientes = new ArrayList<>();

    protected SistemaLeilao() throws RemoteException {
        super();
    }

    @Override
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente, InterfaceCliente cliente) throws RemoteException {
        clientes.add(cliente);
        System.out.println("Cliente registrado: " + nomeCliente);
        return new RespostaNotificacao("Bem-vindo ao leilão, " + nomeCliente + "!", true);
    }

    @Override
    public double consultarMaiorLance() throws RemoteException {
        return 0.0;
    }

    @Override
    public String consultarHistorico() throws RemoteException {
        return "Nenhum histórico.";
    }

    @Override
    public boolean ofertarLance(double lance) throws RemoteException {
        // Quando um lance é ofertado, notifica todos os clientes
        for (InterfaceCliente c : clientes) {
            c.receberNotificacao("Novo lance ofertado: R$ " + lance);
        }
        return true;
    }

    @Override
    public void sairDoLeilao() throws RemoteException {
        System.out.println("Cliente saiu do leilão.");
    }

    @Override
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarReceberNotificacao'");
    }
}
