package set4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RemetenteUDP{
    public static void main(String[] args) {
        
        if(args.length != 3){
            System.out.println("Uso correto: <Nome da maquina> <Porta> <Mensagem>");
            System.exit(0);
        }
        try {
            InetAddress addr = InetAddress.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            byte[] msg = args[2].getBytes();
            DatagramPacket pkg = new DatagramPacket(msg,msg.length,addr, port);
            DatagramSocket ds = new DatagramSocket();
            ds.send(pkg);
            System.out.println("Mensagem enviada para: " + addr.getHostAddress() + "\n"+ "Porta: " + port + "\n" + "Mensagem: " + args[2]);
            ds.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}