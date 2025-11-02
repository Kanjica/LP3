package rmi.leilao;

import java.io.Serializable;
import java.rmi.RemoteException;

public record Lance(InterfaceCliente cliente, Double lanceFeito) implements Serializable {
    private static final long serialVersionUID = 400L; 

    public String toString(){
        String string = "";
        try{
            string = "Cliente: " + cliente.getNome() + " fez o lance: " + lanceFeito;
        } catch (RemoteException e){

        }
        return string;
    }
}
