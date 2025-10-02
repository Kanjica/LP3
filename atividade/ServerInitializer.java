import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public final class ServerInitializer {
    
    private static CountDownLatch latch;
    private static List<PrintWriter> clients = new CopyOnWriteArrayList<>();
    private static final int PORT = 1234;
    
    public ServerInitializer(){
        latch = new CountDownLatch(4);
    }

    public void startServer() throws IOException{
        System.out.println("Servidor Principal Online: Pronto para aceitar conexões (Socket.bind())");
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                    Socket socket = server.accept();
                    System.out.println("[Servidor] Conexão de: " + socket.getInetAddress().getHostAddress());
                    new Thread(new ClientHandler(socket)).start();
                }
        } catch (Exception e) {

        } 
    }

    public void waitForInitialization() throws InterruptedException{
        System.out.println("Esperando Inicialização dos Modulos");
        latch.await();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public static class ClientHandler implements Runnable{

        private final Socket socket;
        private PrintWriter out;
        private String name;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                clients.add(out);

                out.println("Bem-vindo! Digite mensagens. Use 'exit' para sair.\nDigite seu nick: ");
                this.name = in.readLine();

                broadcast("User " + this.name + " entrou no chat.", out, true);

                String userHeader = "[" + this.name + "]: ";

                String line;
                while ((line = in.readLine()) != null) {
                    if ("exit".equalsIgnoreCase(line.trim())) {
                        out.println("\nVocê saiu do chat. Até logo!");

                        broadcast("\nUser " + this.name + " saiu do chat.", out, true);

                        clients.remove(out);
                        throw new IOException();
                    }
                    broadcast(userHeader + line, out, false);

                }
            } catch (IOException e) {
                System.out.println("Cliente desconectou: " + e.getMessage());
            } finally {
                try { socket.close(); } catch (IOException ignore) {}
            }
        }

        private void broadcast(String msg, PrintWriter sender, Boolean serverMessage) {
            for (PrintWriter pw : clients) {
                if(!serverMessage){
                    if (pw != sender) {
                        pw.println(msg);
                    }
                }
                else{
                    pw.println("[Servidor]: " + msg);
                }
            }
        }
    }
}