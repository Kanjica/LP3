package set9;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            InetAddress addr = InetAddress.getByName("localhost");
            DatagramSocket ds = new DatagramSocket();

            byte[] enviaData = new byte[1024];
            byte[] recebeData = new byte[1024];

            int n1 = scanner.nextInt();
            scanner.nextLine();

            int n2 = scanner.nextInt();
            scanner.nextLine();

            char op = scanner.next().charAt(0);

            String mensagem = "" + n1 + n2 + op;
            
            enviaData = mensagem.getBytes();
            DatagramPacket sendPkg = new DatagramPacket(enviaData,enviaData.length,addr, 12345);
            ds.send(sendPkg);

            DatagramPacket recievePkg = new DatagramPacket(recebeData, recebeData.length);

            ds.receive(recievePkg);

            String mensagemRecebida = new String(recievePkg.getData());

            System.out.println("O resultado é : " + mensagemRecebida);
            ds.close();
        } catch (Exception e) {
            System.out.println("Exceção ai pvt: " + e.getMessage());
        }
    }
    
}