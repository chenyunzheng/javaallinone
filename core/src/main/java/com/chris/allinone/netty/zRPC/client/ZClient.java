package com.chris.allinone.netty.zRPC.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author chrischen
 * @date 2025/6/25
 * @desc ZClient
 */
@Slf4j
public class ZClient {

    private final EventLoopGroup group;

    public ZClient() {
        this.group = new NioEventLoopGroup();
    }

    public void connect(String host, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                })
                .remoteAddress(new InetSocketAddress(host, port));
        ChannelFuture channelFuture = bootstrap.connect().sync();
        log.info("connect success");
        channelFuture.channel().closeFuture().sync();
    }
}
