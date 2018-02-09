package io.ayang.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author xanth
 */
public class FileListHttpServer {

    public static void main(String[] args) throws Exception {
        new FileListHttpServer(9099).run();
    }

    private int port ;

    public FileListHttpServer(int port){
        this.port = port;
    }
    public void run()throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        try {
            b.option(ChannelOption.SO_BACKLOG,1024);

            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new FileListHttpServerInitializer());

            Channel f = b.bind(port).sync().channel();
            System.out.println("it is time to open browser");
            f.closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
