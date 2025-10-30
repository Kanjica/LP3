package tpc.ComunicaçãodeStringBasica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    static final int PORT = 12345;
    static final String HOST = "localhost";
    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            String mensagem = "Bananuchi";

            out.println(mensagem);
            String resposta = in.readLine();

            System.out.println(resposta);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
