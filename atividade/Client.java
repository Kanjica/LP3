import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) {

        try {
            Socket socket = new Socket(HOST, PORT);

            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

            AtomicBoolean running = new AtomicBoolean(true);

            Thread thread = new Thread(()->{
                System.out.println("jsoup");
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
