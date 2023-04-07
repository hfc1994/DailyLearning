import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Created by hfc on 2023/4/5.
 */
public class CompressDemo {

    public static byte[] compress(byte[] data) throws IOException {
        Deflater compress = new Deflater();
        compress.setInput(data);
        compress.finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[128];
        while (!compress.finished()) {
            int length = compress.deflate(buf);
            baos.write(buf, 0, length);
        }

        compress.end();
        baos.close();
        return baos.toByteArray();
    }

    public static byte[] unCompress(byte[] data) throws DataFormatException, IOException {
        Inflater unCompress = new Inflater();
        unCompress.setInput(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[128];
        while (!unCompress.finished()) {
            int length = unCompress.inflate(buf);
            // 0 值返回表示需要调用 needsInput() 或者 needsDictionary() 来判断是否需要继续加数据或者需要字典
            // 也可能是其它异常出错的场景
            // 否则可能会出现无限循环
            if (length == 0) {
                break;
            }
            baos.write(buf, 0, length);
        }

        unCompress.end();
        baos.close();
        return baos.toByteArray();
    }

    /**
     * 当传入的数据是不完整的，只是压缩数据的一部分，那么解压缩就可能出现异常
     */
    public static byte[] unCompressMayLoop(byte[] data) throws DataFormatException, IOException {
        Inflater unCompress = new Inflater();
        unCompress.setInput(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[128];
        int cnt = 0;
        while (!unCompress.finished()) {
            int length = unCompress.inflate(buf);
            if (cnt++ > 100) {
                System.out.println("loop more than 100 times");
                System.out.println("needInput(): " + unCompress.needsInput());
                break;
            }
            baos.write(buf, 0, length);
        }

        unCompress.end();
        baos.close();
        return baos.toByteArray();
    }

    public static void main(String[] args) throws DataFormatException, IOException {
        String content = "Hello world."
                + "The quick brown fox jumps over a lazy dog."
                + "The quick brown fox jumps over a lazy dog."
                + "The quick brown fox jumps over a lazy dog."
                + "The quick brown fox jumps over a lazy dog."
                + "The end.";
        byte[] src = content.getBytes(StandardCharsets.UTF_8);
        System.out.println("source length: " + src.length);
        byte[] afterCompress = compress(src);
        System.out.println("after compress: " + afterCompress.length);
        byte[] afterUnCompress = unCompress(afterCompress);
        System.out.println("after unCompress: " + afterUnCompress.length);
        System.out.println("end content: " + new String(afterUnCompress));

        System.out.println();

        byte[] partData = new byte[64];
        System.arraycopy(afterCompress, 0, partData, 0, 64);
        // 不完整的压缩后的数据可以导致解压异常
        afterCompress = unCompressMayLoop(partData);
        System.out.println("part data after unCompress: " + afterCompress.length);
        System.out.println("part data end content: " + new String(afterCompress));
    }

}
