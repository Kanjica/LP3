package rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Servidor {
        public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        InterfaceLeilao sistemaLeilao = new SistemaLeilao();
        Registry registry = LocateRegistry.createRegistry(5000);
        registry.rebind("SistemaLeilao", sistemaLeilao);
        
    }
}
