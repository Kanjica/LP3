import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

import javax.swing.JOptionPane;

public class ClienteTCPBasico {
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 12345);
            ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
            Date data = (Date) in.readObject();
            JOptionPane.showMessageDialog(null, "Data do servidor: " + data.toString());
            in.close();
            System.out.println("Conexão encerrada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}