package rmi.calculadora;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements Calculadora {
    
    protected Servidor() throws RemoteException{
        super();
    }

    @Override
    public double soma(double x, double y) throws RemoteException {
        return x+y;
    }

    @Override
    public double subtracao(double x, double y) throws RemoteException {
        return x-y;
    }

    @Override
    public double multiplicacao(double x, double y) throws RemoteException {
        return x*y;
    }

    @Override
    public double divisao(double x, double y) throws RemoteException {
        if(x == 0 || y == 0){
            throw new ArithmeticException("Divisão por 0 não possível");
        }
        return x/y;
    }
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(5010);
        Calculadora calculadora = new Servidor();

        registry.rebind("Calculadora", calculadora);
    }
}
