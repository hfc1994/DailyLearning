package IO.NIO;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by hfc on 2023/3/12.
 */
public class BufferDemo {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(32);

        System.out.println(buffer); // pos=0, limit=32, cap=32

        buffer.position(5);
        System.out.println(buffer); // pos=5, limit=32, cap=32
        System.out.println();

        buffer.mark();
        buffer.position(10);
        System.out.println("--- before reset: " + buffer); // pos=10, limit=32, cap=32
        buffer.reset();
        System.out.println("---  after reset: " + buffer); // pos=5, limit=32, cap=32

        buffer.clear();
        buffer.position(10);
        buffer.limit(20);
        System.out.println("--- before rewind: " + buffer); // pos=10, limit=20, cap=32
        buffer.rewind();
        System.out.println("---  after rewind: " + buffer); // pos=0, limit=20, cap=32
        System.out.println();

        buffer.clear();
        buffer.put("abcd".getBytes(StandardCharsets.UTF_8));
        System.out.println("---  before compact: " + buffer);   // pos=4, limit=32, cap=32
        System.out.println(new String(buffer.array())); // abcd
        buffer.flip();
        System.out.println("---      after flip: " + buffer);   // pos=0, limit=4, cap=32
        System.out.println((char) buffer.get());   // a
        System.out.println((char) buffer.get());   // b
        System.out.println((char) buffer.get());   // c
        // get() 影响 position 的值
        System.out.println("--- after three get: " + buffer);   // pos=3, limit=4, cap=32
        System.out.println(new String(buffer.array())); // abcd
        buffer.compact();
        System.out.println("---   after compact: " + buffer);   // pos=1, limit=32, cap=32
        // compact会把未读的移到开头，导致idx=0被覆盖，后续的idx没被覆盖，实际信息都还在
        System.out.println(new String(buffer.array())); // dbcd
        buffer.clear();
        System.out.println("---     after clear: " + buffer);   // pos=0, limit=32, cap=32
        System.out.println(new String(buffer.array())); // dbcd
        System.out.println();

        buffer.put((byte) 'a').put((byte) 'b').put((byte) 'c')
                .put((byte) 'd').put((byte) 'e').put((byte) 'f');
        System.out.println("--- before flip: " + buffer);   // [pos=6 lim=32 cap=32]
        buffer.flip();
        System.out.println("--- before get: " + buffer);    // [pos=0 lim=6 cap=32]
        System.out.println((char) buffer.get(2));   // c
        // get(idx) 不影响 position 的值
        System.out.println("--- after get(2): " + buffer);  // [pos=0 lim=6 cap=32]
        byte[] dst = new byte[2];
        buffer.get(dst, 0, 2);
        // get(dst,from,to) 影响 position 的值，内部连续调用了 get()
        System.out.println("--- after get(dst,from,to): " + buffer);    // [pos=2 lim=6 cap=32]
        System.out.println(new String(dst));    // ab
        System.out.println(new String(buffer.array())); // abcdef
        System.out.println();

        buffer = ByteBuffer.allocate(32);
        System.out.println("--- before put(byte): " + buffer);  // [pos=0 lim=32 cap=32]
        buffer.put((byte) 'z');
        // put(byte) 影响 position 的值
        System.out.println("--- after put(byte): " + buffer);   // [pos=1 lim=32 cap=32]
        buffer.put(2, (byte) 'x');
        // put(idx,byte) 不影响 position 的值
        System.out.println("--- after put(idx,byte): " + buffer);   // [pos=1 lim=32 cap=32]
        System.out.println(new String(buffer.array())); // z x
        ByteBuffer tmp = ByteBuffer.allocate(5);
        tmp.put("hahah".getBytes(StandardCharsets.UTF_8));
        buffer.put(tmp);
        // Buffer 不通过 flip 转换模式就会读取不到数据
        System.out.println("--- after put(Buffer): " + buffer); // [pos=1 lim=32 cap=32]
        System.out.println(new String(buffer.array())); // z x
        tmp.flip();
        buffer.put(tmp);
        System.out.println("--- after put(Buffer): " + buffer); // [pos=6 lim=32 cap=32]
        System.out.println(new String(buffer.array())); // zhahah
        System.out.println();

        System.out.println("--- before flip: " + buffer);
        System.out.println("remaining: " + buffer.remaining());
        System.out.println("arrayOffset: " + buffer.arrayOffset());
        buffer.flip();
        System.out.println("--- after flip: " + buffer);
        System.out.println("remaining: " + buffer.remaining());
        System.out.println("arrayOffset: " + buffer.arrayOffset());

        buffer = ByteBuffer.allocate(32);
        buffer.put(2, (byte) 'd');
        System.out.println(buffer.hasArray());
        System.out.println(buffer.arrayOffset());
    }

}
