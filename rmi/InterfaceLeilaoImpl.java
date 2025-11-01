package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InterfaceLeilaoImpl extends UnicastRemoteObject implements InterfaceLeilao {

    private List<InterfaceCliente> clientes = new CopyOnWriteArrayList<>();
    private List<Lance> lances = new CopyOnWriteArrayList<>();
    private ReadWriteLock locksLance = new ReentrantReadWriteLock();
    private ReadWriteLock locksCliente = new ReentrantReadWriteLock();

    private InterfaceCliente interfaceUsadaParaBostadeNada = new ClienteImpl("servidor");
    protected InterfaceLeilaoImpl() throws RemoteException {
        super();
    }

    @Override
    public RespostaNotificacao registrarReceberNotificacao(String nomeCliente, InterfaceCliente cliente) throws RemoteException {
        clientes.add(cliente);
        System.out.println("Cliente registrado: " + nomeCliente);
        return new RespostaNotificacao("Bem-vindo ao leilão, " + nomeCliente + "!", true);
    }

    @Override
    public synchronized Lance consultarMaiorLance() throws RemoteException {
        try{
            Lance lance = lances.getLast();   
            return lance; 
        } catch (NoSuchElementException e){
            System.out.println("Errin");
            return new Lance(interfaceUsadaParaBostadeNada, 0.0);
        }
    }

    @Override
    public String consultarHistorico() throws RemoteException {
        StringBuilder sb = new StringBuilder();

        for (Lance lance : lances) {
            sb.append("Cliente: " + lance.cliente().getNome() + " fez o lance: " + lance.lanceFeito() + "\n");
        }
        return sb.toString();
    }

    @Override
    public boolean ofertarLance(double lanceFeito, InterfaceCliente cliente) throws RemoteException {
        
        locksLance.writeLock().lock(); 
        boolean sucesso;

        try {
            Lance ultimoLance = null;
            try{ultimoLance = lances.getLast();} catch(NoSuchElementException e){}
                    
            if(ultimoLance == null || lanceFeito > ultimoLance.lanceFeito()){
                Lance novoLance = new Lance(cliente, lanceFeito);
                lances.add(novoLance);

                System.out.println("[SERVER LOG] NOVO LANCE de R$ " + lanceFeito + " por: " + cliente.getNome());
                sucesso = true;
            } else {
                cliente.receberNotificacao("Seu lance foi inválido. O último lance foi R$ " + lances.getLast().lanceFeito());
                sucesso = false;
            }
        } finally {
            locksLance.writeLock().unlock(); 
        }

        if (sucesso) {
            locksCliente.writeLock().lock(); 
            try {
                List<InterfaceCliente> clientesAtivos = new ArrayList<>();
                for (InterfaceCliente c : clientes) {
                    try {
                        c.receberNotificacao(cliente.getNome()+ " ofertou um novo lance: R$ " + lanceFeito);
                        clientesAtivos.add(c); 
                    } catch (RemoteException e) {
                        System.out.println("[SERVER LOG] Cliente inativo removido: " + c.getNome()); 
                    }
                }
                this.clientes = clientesAtivos; 
            } finally {
                locksCliente.writeLock().unlock(); 
            }
        }

        return sucesso;
    }

    @Override
    public void sairDoLeilao(InterfaceCliente cliente) throws RemoteException {
        cliente.receberNotificacao("Saindo do servidor");
        clientes.remove(cliente);
    }
}
