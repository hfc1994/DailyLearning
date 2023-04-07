package IO.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2023/3/14.
 */
public class ChannelDemo {

    public static void main(String[] args) throws Exception {
//        fileDemo1();
//        fileDemo2();
//        fileDemo3();
//        UDPDemo();
//        pipeDemo();
//        asyncChannelDemo1();
        asyncChannelDemo2();
    }

    // 利用通道和非直接缓冲区完成
    private static void fileDemo1() throws IOException {
        FileInputStream fis = new FileInputStream("F:\\tmp\\abc.txt");
        FileOutputStream fout = new FileOutputStream("F:\\tmp\\cba.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (inChannel.read(buffer) != -1) {
            buffer.flip();
            outChannel.write(buffer);
            buffer.clear();
        }
    }

    // 利用通道和内存映射文件完成
    private static void fileDemo2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\tmp\\abc.txt"),
                StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\tmp\\cba.txt"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        // 看方法注释，通过map方法把文件映射到内存，一般会比常规的read/write（也就是fileDemo1）方式代价更大
        MappedByteBuffer inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] data = new byte[inBuffer.limit()];
        inBuffer.get(data);
        outBuffer.put(data);
        inChannel.close();
        outChannel.close();
    }

    // 通道之间的直接传输
    private static void fileDemo3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("F:\\tmp\\abc.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("F:\\tmp\\cba.txt"),
                StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    // 离散读和聚集写
    private static void channelDemo1(SocketChannel channel) throws IOException {
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body = ByteBuffer.allocate(1024);

        ByteBuffer[] bufArray = { header, body };
        // NIO 的离散读
        // 当 buf1 被写满后，channel 会转而开始向 buf2 写数据，直到 bufArray 全部被写满
        channel.read(bufArray);
        // NIO 的聚集写
        // 当 buf1 中的数据被读取完（假设 header 只有 60 字节的数据，那么只会读取 60 字节，剩余的 68 字节会被忽略），
        // channel 会转而开始读取 buf2 的数据，直到 bufArray 全部被读取
        channel.write(bufArray);
    }

    // UDP的demo
    private static void UDPDemo() {
        int udpPort = 10086;
        Thread server = new Thread(() -> {
            System.out.println("server starting");
            try (DatagramChannel udp = DatagramChannel.open()) {
                udp.socket().bind(new InetSocketAddress(udpPort));
                ByteBuffer buf = ByteBuffer.allocate(256);

                while (true) {
                    // 接收的数据如果大于 buf 的大小，超出部分会被静默丢弃
                    udp.receive(buf);
                    buf.flip();
                    System.out.println("receive:" + new String(buf.array()));
                    buf.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.start();

        Thread client = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("client starting");
            try (DatagramChannel udp = DatagramChannel.open()) {
                ByteBuffer buf = ByteBuffer.allocate(256);

                int i = 0;
                while (true) {
                    buf.put(("hi server " + i++).getBytes(StandardCharsets.UTF_8));
                    buf.flip();
                    // UDP 是面向数据包的，不需要提前建立连接
                    int sendCnt = udp.send(buf, new InetSocketAddress("127.0.0.1", udpPort));
                    System.out.println("client send: " + sendCnt);
                    buf.clear();
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        client.start();

    }

    // 线程间通信
    private static void pipeDemo() throws IOException {
        Pipe p = Pipe.open();
        Thread readT = new Thread(() -> {
            Pipe.SourceChannel source = p.source();
            ByteBuffer buf = ByteBuffer.allocate(128);
            try {
                while (true) {
                    int byteCnt = source.read(buf);
                    System.out.println("client receive cnt: " + byteCnt);
                    buf.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readT.start();

        Thread writeT = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Pipe.SinkChannel sink = p.sink();
            ByteBuffer buf = ByteBuffer.allocate(128);
            int i = 0;
            try {
                while (true) {
                    buf.clear();
                    buf.put(("hi server " + i++).getBytes(StandardCharsets.UTF_8));
                    buf.flip();
                    sink.write(buf);
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        writeT.start();
    }

    // 异步通道
    private static void asyncChannelDemo1() throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("F:\\tmp\\abc.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        Future<Integer> op = fileChannel.read(buffer, position);

        while (!op.isDone()) {
            TimeUnit.MILLISECONDS.sleep(10);
        }

        buffer.flip();
        byte[] data = new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        // 实际内容大于 1024 字节，最终这个 opsition 就是 1024
        System.out.println("end position: " + op.get());
        buffer.clear();
    }

    // 使用 CompletionHandler
    private static void asyncChannelDemo2() throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("F:\\tmp\\cba.txt");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        long position = 0;

        buffer.put("hello everyone".getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        CountDownLatch latch = new CountDownLatch(1);
        fileChannel.write(buffer, position, "bufferNameOne", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println("fileName=" + attachment + ", file written success, size: " + result);
                latch.countDown();
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("fileName=" + attachment + ", fail to write file, cause: " + exc.getMessage());
                latch.countDown();
            }
        });

        latch.await();
        System.out.println("write file over");
    }

}
