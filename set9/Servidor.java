package set9;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class Servidor {

    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(12345);
            byte[] receive = new byte[1024];
            byte[] send = new byte[1024];
            
            while(true){
                DatagramPacket inputPkg = new DatagramPacket(receive, receive.length);
                ds.receive(inputPkg);
                String[] str = new String(inputPkg.getData(), StandardCharsets.UTF_8).split(" ");
                JOptionPane.showMessageDialog(null, str);
                try {
                    int res = new Calc(
                        Integer.parseInt(str[0]),
                        Integer.parseInt(str[2]),
                        str[1].charAt(0)).calcular();

                        send = String.valueOf(res).getBytes();

                        DatagramPacket exitPkg = new DatagramPacket(send, send.length, inputPkg.getAddress(), inputPkg.getPort());

                        ds.send(exitPkg);

                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
