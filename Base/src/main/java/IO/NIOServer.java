package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * Created by hfc on 2023/3/1.
 *
 * 其它写法：https://cloud.tencent.com/developer/article/1509266
 */
public class NIOServer {

    public static final String IP = "127.0.0.1";
    public static final int PORT = 10010;

    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.bind(new InetSocketAddress(PORT), 1024);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the server start in port: " + PORT);

            doBiz(selector);
        } catch (IOException e) {
            e.printStackTrace();
            doClean(selector, serverChannel);
            return;
        }

    }

    private static void doBiz(Selector selector) {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handleSelectionKey(selector, key);
                }

                keys.clear();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static void handleSelectionKey(Selector selector, SelectionKey key) {
        if (!key.isValid()) {
            return;
        }

        // 处理新接入的请求信息
        if (key.isAcceptable()) {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            try {
                // 接受新的连接
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                // 把新连接注册到selector上
                sc.register(selector, SelectionKey.OP_READ);
                System.out.println("new connection from: " + sc.getRemoteAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (key.isReadable()) {
            // 读取数据
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer readBuf = ByteBuffer.allocate(1024);

            /*
             * 由于我们已经将SocketChannel设置为异步非阻塞模式，因此这里的read()是非阻塞的
             * 使用返回值进行判断，有三种可能的结果
             * 返回值大于0：读取到字节，对字节进行编解码
             * 返回值等于0：没有读取到字节，属于正常场景，忽略（TODO 也可能是读取完了）
             * 返回值等于-1：链路已经关闭，需要关闭SocketChannel，释放资源（TODO -1 貌似只是 the channel has reached end-of-stream ）
             */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                while (true) {
                    int cnt = sc.read(readBuf);
                    if (cnt > 0) {
                        // 用于后续对缓冲区的读操作
                        readBuf.flip();
                        byte[] bytes = new byte[cnt];
                        readBuf.get(bytes);
                        baos.write(bytes, 0, cnt);
                    } else if (cnt < 0) {
                        // 对端链路关闭
                        key.channel();
                        sc.close();
                        baos.close();
                        baos = null;
                        break;
                    } else {
                        // 读到0字节，说明后面没数据了
                        break;
                    }
                }

                if (baos != null) {
                    System.out.println("server recv: " + baos.toString());
                    doWrite(sc, "recv message, over");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (sc != null) {
                    try {
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void doClean(Selector selector, ServerSocketChannel serverChannel) {
        if (serverChannel != null) {
            try {
                serverChannel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void doWrite(SocketChannel channel, String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        writeBuf.put(bytes);
        writeBuf.flip();
        channel.write(writeBuf);
    }
}
