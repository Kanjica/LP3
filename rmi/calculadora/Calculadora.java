package rmi.calculadora;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculadora extends Remote{
    public double soma(double x, double y) throws RemoteException;
    public double subtracao(double x,double y) throws RemoteException;
    public double multiplicacao(double x, double y) throws RemoteException;
    public double divisao(double x, double y) throws RemoteException;
}
