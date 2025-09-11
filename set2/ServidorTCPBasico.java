package set2;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServidorTCPBasico {

    public static void main(String[] args) {
        try {
            ServerSocket servidor = new ServerSocket(12345);   
            System.out.println("Servidor ouvindo a porta 12345");
            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
                out.flush();
                out.writeObject(new Date());
                out.close();
                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}