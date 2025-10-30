package tpc.ComunicaçãodeStringBasica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    static final int PORT = 12345;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor ativo na porta 12345");
            Socket cliente = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream()), true);

            String textoRecebido = in.readLine();
            System.out.println(textoRecebido);

            out.println(textoRecebido);
            cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}