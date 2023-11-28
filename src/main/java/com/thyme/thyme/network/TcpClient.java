package com.thyme.thyme.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TcpClient {

    int port;
    Channel channel;
    EventLoopGroup workGroup = new NioEventLoopGroup();

    /**
     * Constructor
     */
    public TcpClient(int port){
        this.port = port;
    }

     /**
     * 	Startup the client
     * 
     * @return {@link ChannelFuture} 
     * @throws Exception
     */
    public ChannelFuture startup() throws Exception {
        try{
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture channelFuture = b.connect("127.0.0.1", this.port).sync();
            this.channel = channelFuture.channel();

            return channelFuture;
        }finally{
        }
    }


    
}
