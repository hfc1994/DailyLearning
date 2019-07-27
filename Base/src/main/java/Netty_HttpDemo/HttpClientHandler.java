package Netty_HttpDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * Created by hW3838 on 2018/4/10.
 */
public class HttpClientHandler extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("---active----");

        URI uri = new URI("/");
        String message = "{\"result\":\"0\",\"response\":\"success\"}";
        ByteBuf buf = ctx.channel().alloc().buffer();
        buf.writeBytes(message.getBytes());

        FullHttpRequest req = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri.toASCIIString(), buf);
        req.headers().set(HttpHeaderNames.HOST, "127.0.0.1");
        req.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        req.headers().set(HttpHeaderNames.CONTENT_ENCODING, "utf-8");
        req.headers().set(HttpHeaderNames.ACCEPT, "application/json");
        req.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        req.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(buf.readableBytes()));


        ctx.writeAndFlush(req);

//        ByteBuf buf = ctx.channel().alloc().buffer();
//        buf.writeBytes(message.getBytes());
//        ctx.writeAndFlush(buf);

        if (req.refCnt() > 0)
            req.release();

        System.out.println("---send-over---");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        System.out.println("---read----");

        if (msg instanceof FullHttpResponse)
        {
            System.out.println("FullHttpResponse");

            FullHttpResponse res = (FullHttpResponse) msg;
            byte[] b = new byte[res.content().readableBytes()];
            res.content().readBytes(b);
            String content = new String(b, "utf-8");

            System.out.println("---" + content);
        }
        else
        {
            System.out.println("---nothing---");
        }
        System.out.println("----over-----");
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        System.out.println("something wrong : " + cause.getMessage());
        ctx.close();
    }
}
