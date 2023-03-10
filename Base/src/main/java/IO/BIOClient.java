package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2023/2/28.
 */
public class BIOClient {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 10086;

    public static void main(String[] args) throws InterruptedException {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        while (true) {
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("how are you");
                String recv = reader.readLine();
                System.out.println("recv: " + recv);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                doClean(reader, writer, socket);
            }

            TimeUnit.SECONDS.sleep(5);
        }
    }

    private static void doClean(BufferedReader reader, PrintWriter writer, Socket socket) {
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

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
