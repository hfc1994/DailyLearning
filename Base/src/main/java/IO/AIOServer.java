package IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/**
 * Created by hfc on 2023/3/24.
 */
public class AIOServer {

    public static void main(String[] args) {
        Thread t = new Thread(new AsyncServer());
        t.start();
    }

    public static class AsyncServer implements Runnable {

        private int port = 10000;
        private CountDownLatch latch = new CountDownLatch(1);
        public AsynchronousServerSocketChannel asyncServerSocket;

        @Override
        public void run() {
            try {
                asyncServerSocket = AsynchronousServerSocketChannel.open();
                asyncServerSocket.bind(new InetSocketAddress(port));
                System.out.println("the server start in port: " + port);
                asyncServerSocket.accept(this, new AcceptCompletionHandler());
                latch.await();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServer> {

        @Override
        public void completed(AsynchronousSocketChannel channel, AsyncServer attachment) {
            attachment.asyncServerSocket.accept(attachment, this);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer, buffer, new ReadCompletionHandler(channel));
        }

        @Override
        public void failed(Throwable exc, AsyncServer attachment) {
            exc.printStackTrace();
            attachment.latch.countDown();
        }
    }

    public static class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel channel;

        public ReadCompletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            byte[] body = new byte[attachment.remaining()];
            attachment.get(body);

            System.out.println("recv:" + new String(body));

            String strResp = "response from async server";
            ByteBuffer respBuf = ByteBuffer.allocate(1024);
            respBuf.put(strResp.getBytes(StandardCharsets.UTF_8));
            respBuf.flip();
            channel.write(respBuf, respBuf, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("write succeed");
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println("fail to write to channel: " + exc.getMessage());
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            System.out.println("read with exception: " + exc.getMessage());
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
