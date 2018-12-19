package Netty_HttpDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by hW3838 on 2018/4/10.
 */
public class HttpServerDemo
{
    public static int iPort = 20000;

    public static void main(String... args)
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap boot = new ServerBootstrap();
        boot.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                //.option(ChannelOption.SO_SNDBUF, 64 * 1024)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception
                    {
                        ch.pipeline().addLast("http-req-decoder", new HttpRequestDecoder());
                        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                        ch.pipeline().addLast("http-res-encoder", new HttpResponseEncoder());
                        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        ch.pipeline().addLast("http-handler", new HttpServerHandler());
                    }
                });

        try
        {
            ChannelFuture f = boot.bind(iPort).sync();
            System.out.println("启动完成");
            f.channel().closeFuture().sync();
            System.out.println("服务结束");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
