package set4;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;

public class ReceptorUDP {
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Informe a porta a ser ouvida");
            System.exit(0);
        }
        try {
            int port = Integer.parseInt(args[0]);
            DatagramSocket ds = new DatagramSocket(port);
            byte[] msg = new byte[256];
            DatagramPacket pkg = new DatagramPacket(msg, msg.length);
            ds.receive(pkg);

            JOptionPane.showMessageDialog(null,new String(pkg.getData()).trim(), "Mensagem recebida", 1);
            ds.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
