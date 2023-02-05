package Netty_HttpDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by hfc on 2018/4/10.
 */
public class HttpClientDemo {

    public static void main(String... args) {
        // 客户端只需要一个事件循环组，可以看作 BossGroup
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap boot = new Bootstrap();
        boot.group(workerGroup)
                // 说明客户端通道的实现类（便于 Netty 做反射处理）
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                .option(ChannelOption.SO_SNDBUF, 1024 * 1024)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT)
                // 用于给 BossGroup 设置业务处理器
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("http-res-decoder", new HttpResponseDecoder());
                        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                        ch.pipeline().addLast("http-req-encoder", new HttpRequestEncoder());
                        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        ch.pipeline().addLast("http-handler", new HttpClientHandler());
                    }
                });

        try {
            ChannelFuture f = boot.connect("127.0.0.1", HttpServerDemo.iPort).sync();
            System.out.println("连接成功");
            f.channel().closeFuture().sync();
            System.out.println("连接断开");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
