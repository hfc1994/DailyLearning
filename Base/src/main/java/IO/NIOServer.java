package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
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

    private static ByteBuffer readBuf = ByteBuffer.allocate(1024);
    private static ByteBuffer writeBuf = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            // backlog 处于等待中的最大连接数量
            serverChannel.bind(new InetSocketAddress(PORT), 1024);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the server start in port: " + PORT);

            doBiz(selector);
        } catch (IOException e) {
            e.printStackTrace();
            doClean(selector, serverChannel, null);
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

            /*
             * 由于我们已经将SocketChannel设置为异步非阻塞模式，因此这里的read()是非阻塞的
             * 使用返回值进行判断，有三种可能的结果
             * 返回值大于0：读取到字节，对字节进行编解码
             * 返回值等于0：没有读取到字节，或者已经读取完了，属于正常场景，忽略
             * 返回值等于-1：链路已经关闭，需要关闭SocketChannel，释放资源
             *              -1 表示 the channel has reached end-of-stream（ReadableByteChannel），也就是已经关闭
             */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                int inputOffset = 0;
                while (true) {
                    readBuf.clear();
                    int cnt = sc.read(readBuf);
                    if (cnt > 0) {
                        // 用于后续对缓冲区的读操作
                        readBuf.flip();
                        byte[] bytes = new byte[cnt];
                        readBuf.get(bytes);
                        baos.write(bytes, inputOffset, cnt);
                        inputOffset += cnt;
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

                if (baos != null && baos.size() > 0) {
                    System.out.println("server recv: " + baos.toString());
                    // 简单场景确实可以在 OP_READ 事件里面直接 write 数据回客户端
                    // 但是有概率在写缓冲区满时无法写入数据，因此使用 OP_WRITE 事件才是最正确的
                    // https://blog.csdn.net/huyuyang6688/article/details/126106949
//                    doWrite(sc, "recv message, over");
                    sc.register(selector, SelectionKey.OP_WRITE);
                }
            } catch (IOException e) {
                e.printStackTrace();
                doClean(null, sc, baos);
            }
        } else if (key.isWritable()) {
            SocketChannel sc = (SocketChannel) key.channel();
            try {
                doWrite(sc, "writable status, over");
                sc.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void doWrite(SocketChannel channel, String content) throws IOException {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        writeBuf.clear();
        writeBuf.put(bytes);
        writeBuf.flip();
        channel.write(writeBuf);
    }

    private static void doClean(Selector selector, SelectableChannel channel, OutputStream stream) {
        if (channel != null) {
            try {
                channel.close();
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

        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
