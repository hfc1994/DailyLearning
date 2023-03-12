package IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Created by hfc on 2023/3/6.
 *
 * TODO 需要了解下Channel、Selector 和 Buffer 三个元素的各种方法的含义
 * TODO SelectionKey 每个类型的含义
 */
public class NIOClient {

    private static ByteBuffer readBuf = ByteBuffer.allocate(1024);
    private static ByteBuffer writeBuf = ByteBuffer.allocate(1024);;

    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        Selector selector = Selector.open();
        // true表示连接已经建立；false表示channel处于非阻塞模式且连接操作正在进行中
        if (channel.connect(new InetSocketAddress(NIOServer.IP, NIOServer.PORT))) {
            channel.register(selector, SelectionKey.OP_READ);
            doWrite(channel);
        } else {
            channel.register(selector, SelectionKey.OP_CONNECT);
        }

        while (true) {
            selector.select(1000);
            Set<SelectionKey> keys = selector.selectedKeys();
            for (SelectionKey key : keys) {
                handleSelectionKey(key, selector);
            }
            keys.clear();
        }
    }

    private static void handleSelectionKey(SelectionKey key, Selector selector) throws IOException {
        if (!key.isValid()) {
            return;
        }

        SocketChannel channel = (SocketChannel) key.channel();
        if (key.isConnectable()) {
            if (channel.finishConnect()) {
                channel.register(selector, SelectionKey.OP_READ);
                doWrite(channel);
            } else {
                System.exit(-1);    // 连接失败，进程退出
            }
        } else if (key.isReadable()) {
            readBuf.clear();
            int cnt = channel.read(readBuf);
            if (cnt > 0) {
                readBuf.flip();
                byte[] bytes = new byte[readBuf.remaining()];
                readBuf.get(bytes);
                String cont = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("client recv: " + cont);
            } else if (cnt < 0) {
                // 对链路关闭
                key.cancel();
                channel.close();
            } else {
                // 读到 0 字节，忽略
            }
        }
    }

    private static void doWrite(SocketChannel channel) throws IOException {
        String cont = "say hi from client";
        byte[] bytes = cont.getBytes(StandardCharsets.UTF_8);
        writeBuf.clear();
        writeBuf.put(bytes);
        writeBuf.flip();
        channel.write(writeBuf);
        if (writeBuf.hasRemaining()) {
            System.out.println("failed send to server");
        } else {
            System.out.println("succeed send to server");
        }
    }
}
