package Netty_HttpDemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hfc on 2023/3/13.
 */
public class HttpStatisticHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger cnt = new AtomicInteger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        cnt.getAndIncrement();
        System.out.println("do statistic");

        // 自定义的基础 handler，如果不显示调用该方法，则 msg 就不会传递到后一个 handler
        ctx.fireChannelRead(msg);
    }

}
