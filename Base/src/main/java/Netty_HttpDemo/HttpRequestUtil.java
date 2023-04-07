package Netty_HttpDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by hfc on 2023/4/5.
 */
public class HttpRequestUtil {

    public static ByteBuf buildDefaultMsg(Channel ch) throws URISyntaxException {
        String message = "{\"result\":\"0\",\"response\":\"success\"}";
        ByteBuf buf = ch.alloc().buffer();
        buf.writeBytes(message.getBytes());

        return buf;
    }

    public static FullHttpRequest buildRequest(URI uri, ByteBuf buf) {
        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString(), buf);
        req.headers().set(HttpHeaderNames.HOST, "127.0.0.1");
        req.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        req.headers().set(HttpHeaderNames.CONTENT_ENCODING, "utf-8");
        req.headers().set(HttpHeaderNames.ACCEPT, "application/json");
        req.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        req.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));

        return req;
    }

}
