package Netty_HttpDemo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Created by hfc on 2018/4/10.
 */
public class HttpClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("---active----");

        URI uri = new URI("/");
        ByteBuf buf = HttpRequestUtil.buildDefaultMsg(ctx.channel());
        FullHttpRequest req = HttpRequestUtil.buildRequest(uri, buf);
        ctx.writeAndFlush(req);

//        ByteBuf buf = ctx.channel().alloc().buffer();
//        buf.writeBytes(message.getBytes());
//        ctx.writeAndFlush(buf);

        if (req.refCnt() > 0) {
            req.release();
        }

        System.out.println("---send-over---");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("---read----");

        if (msg instanceof FullHttpResponse) {
            System.out.println("FullHttpResponse");

            FullHttpResponse res = (FullHttpResponse) msg;
            byte[] b = new byte[res.content().readableBytes()];
            res.content().readBytes(b);
            String content = new String(b, StandardCharsets.UTF_8);

            System.out.println("---" + content);
        } else {
            System.out.println("---nothing---");
        }
        System.out.println("----over-----");
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("something wrong : " + cause.getMessage());
        ctx.channel().close();
    }
}
