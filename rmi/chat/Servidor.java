package rmi.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Servidor extends UnicastRemoteObject implements InterfaceChat {

    List<InterfaceCliente> clientes = new ArrayList<>();
    List<String> historico = new ArrayList<>();
    InterfaceCliente servidor = new Cliente("SERVIDOR");

    protected Servidor() throws RemoteException {
        super();
    }

    @Override
    public void conectar(InterfaceCliente cliente) throws RemoteException {
        clientes.add(cliente);
        enviarMensagem(servidor, "O mano [" + cliente.getNome() + "] se juntou ao chat.");
    }

    @Override
public void enviarMensagem(InterfaceCliente remetente, String mensagem) throws RemoteException {
    String nome = remetente.getNome(); 
    historico.add("["+ nome + "]: " + mensagem);
    
    List<InterfaceCliente> ativos = new ArrayList<>();
    for(InterfaceCliente c : clientes){
        try {
            if (!c.equals(remetente)) { 
                c.receberMensagem(nome, mensagem);
            }
            ativos.add(c);
        } catch (RemoteException e) {
        }
    }
    this.clientes = ativos;
}

    @Override
    public void desconectar(InterfaceCliente cliente) throws RemoteException {
        clientes.remove(cliente);
        enviarMensagem(servidor, "O mano " + cliente.getNome() + " saiu do chat.");
    }
    
    @Override
    public void visualizarHistorico(InterfaceCliente cliente) throws RemoteException {
        StringBuilder sb = new StringBuilder();
        for (String mensagem : historico) {
            sb.append(mensagem + "\n");
        }
        cliente.receberMensagem("Servidor", sb.toString());
    }

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5020);
        InterfaceChat chat = new Servidor();
        registry.rebind("Chat", chat);
    }

    
}
