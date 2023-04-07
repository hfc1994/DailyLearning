package Netty_HttpDemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by hfc on 2018/4/10.
 */
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("---remote address---" + ctx.channel().remoteAddress());

        if (msg instanceof FullHttpRequest) {
            System.out.println("FullHttpRequest");

            FullHttpRequest req = (FullHttpRequest) msg;

            byte[] b = new byte[req.content().readableBytes()];

            String uri = req.uri();
            ByteBuf response = ctx.alloc().buffer();
            if (HttpMethod.GET.equals(req.method())) {
                if ("/version".equalsIgnoreCase(uri)) {
                    response.writeBytes("version 0.1".getBytes());
                } else if ("/author".equalsIgnoreCase(uri)) {
                    response.writeBytes("hfc".getBytes());
                } else if ("/time".equalsIgnoreCase(uri)) {
                    LocalDateTime ldt = LocalDateTime.now();
                    String strDate = formatter.format(ldt);
                    response.writeBytes(strDate.getBytes());
                }
            } else {
                if ("/".equals(uri)) {
                    req.content().readBytes(b);
                    String jsonStr = new String(b, StandardCharsets.UTF_8);

                    JSONObject json = JSON.parseObject(jsonStr);

                    System.out.println("result = " + json.get("result"));
                    System.out.println("response = " + json.get("response"));

                    response.writeBytes("you did it".getBytes());
                    System.out.println("you did it");
                } else {
                    System.out.println("未知的uri");
                }
            }

            if (response.readableBytes() > 0) {
                // 1
                FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, response);
                res.headers().set(HttpHeaderNames.CONTENT_ENCODING, "utf-8");
                res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                res.headers().set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.readableBytes()));
                ctx.writeAndFlush(res);
                System.out.println("发送数据");
            }

            if (response.refCnt() > 0) {
                System.out.println("--ByteBuf release--" + response.refCnt());
                response.release();
            }

        }
        System.out.println("----over-----");
    }

}
