package Thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user-hfc on 2018/6/14.
 *
 * @author user-hfc.
 */
class Server implements Runnable
{
    public void run()
    {
        try
        {
            System.out.println("---start----");
            ServerSocket ss = new ServerSocket(9999);
            while (!Thread.interrupted())
                new Thread(new Handler(ss.accept())).start(); //创建新线程来handle
            // or, single-threaded, or a thread pool
        } catch (IOException ex) { /* ... */ }
    }

    public class Handler implements Runnable
    {
        final Socket socket;
        Handler(Socket s) { socket = s; }
        public void run()
        {
            try
            {
                System.out.println("------------");
                byte[] input = new byte[1024];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException ex) { /* ... */ }
        }
        private byte[] process(byte[] cmd) { /* ... */ return null;}
    }

    public static void main(String[] args)
    {
        Thread t = new Thread(new Server());
        t.start();
    }
}
