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
 * Created by hfc on 2018/4/10.
 */
public class HttpServerDemo {

    public static int iPort = 20000;

    public static void main(String... args) {
        // bossGroup 只处理连接请求，workerGroup 处理业务
        // 当前代码只监听一个端口的 accept 事件，也就是只有一个 selector，那么 bossGroup 只需要一个线程即可
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap boot = new ServerBootstrap();
        boot.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // option() 方法用于给服务端的 ServerSocketChannel 添加配置
                .option(ChannelOption.SO_BACKLOG, 1024) // 设置等待连接的队列的容量
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                //.option(ChannelOption.SO_SNDBUF, 64 * 1024)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT)
                // childOption() 方法用于给服务端 ServerSocketChannel 接收到的 SocketChannel 添加配置
                .childOption(ChannelOption.SO_KEEPALIVE, true)  // 设置连接保活
                // handler() 方法用于给 bossGroup 设置业务处理器
                // childHandler() 方法用于给 wokerGroup 设置业务处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("http-req-decoder", new HttpRequestDecoder());
                        ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                        ch.pipeline().addLast("http-res-encoder", new HttpResponseEncoder());
                        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        ch.pipeline().addLast("http-handler", new HttpServerHandler());
                    }
                });

        try {
            ChannelFuture f = boot.bind(iPort).sync();
            System.out.println("启动完成");
            f.channel().closeFuture().sync();
            System.out.println("服务结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
