package IO.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by hfc on 2023/3/14.
 *
 * todo 需要多了解下新的文件API
 */
public class ChannelDemo {

    public static void main(String[] args) throws IOException {
//        fileDemo1();
//        fileDemo2();
        fileDemo3();
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

}
