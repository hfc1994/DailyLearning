package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hfc on 2023/2/28.
 */
public class BIOServer {

    private static final int PORT = 10086;

    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            System.out.println("the server start in port: " + PORT);

            while (true) {
                Socket socket = server.accept();
                InetSocketAddress address = (InetSocketAddress) socket.getRemoteSocketAddress();
                System.out.println("connected: " + address.getAddress().toString());
                Thread t = new Thread(new ServerHandler(socket));
                t.setDaemon(true);
                t.start();
            }
        } finally {
            if (server != null) {
                System.out.println("closing server");
                server.close();
            }
        }

    }

    public static class ServerHandler implements Runnable {

        private Socket socket;

        public ServerHandler(Socket socket) {
            System.out.println("init server handler");
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                writer = new PrintWriter(this.socket.getOutputStream());

                String content = reader.readLine();
                if (content == null) {
                    return;
                }

                System.out.println("recv: " + content);
                writer.println("say again");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.doClean(reader, writer);
            }
        }

        private void doClean(BufferedReader reader, PrintWriter writer) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (writer != null) {
                writer.close();
            }

            if (this.socket != null) {
                try {
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
