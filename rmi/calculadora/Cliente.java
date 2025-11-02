package rmi.calculadora;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("localhost", 5010);
        Calculadora calculadora = (Calculadora) registry.lookup("Calculadora");

        Scanner sc = new Scanner(System.in);
        while (true) {
            try{
                System.out.println("Da o papo pcr: ");
                String comando = sc.nextLine();

                String[] partes = comando.split(" ");

                Double x = Double.parseDouble(partes[1]);
                Double y = Double.parseDouble(partes[2]);

                Double res = 0.0;
                switch (partes[0]) {
                    case "somar" -> {
                        res = calculadora.soma(x, y);
                    }
                    case "subtrair" -> {
                        res = calculadora.subtracao(x, y);
                    }
                    case "multiplicar" -> {
                        res = calculadora.multiplicacao(x, y);
                    }
                    case "dividir" -> {
                        res = calculadora.divisao(x, y);
                    }
                }

                System.out.println("Resutado da operação foi " + res);
            } catch ( ArithmeticException e){
                e.getStackTrace();
            }
        }
    }
}