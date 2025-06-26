package com.chris.allinone.netty.zRPC.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

/**
 * @author chrischen
 * @date 2025/6/25
 * @desc ZServer
 */
public class ZServer {

    private final int port;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public ZServer(int port) {
        int nBossThread = NettyRuntime.availableProcessors() * 2;
        int nWorkerThread = nBossThread;
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(nBossThread);
        this.workerGroup = new NioEventLoopGroup(nWorkerThread);
    }

    public ZServer(int nBossThread, int nWorkerThread, int port) {
        this.port = port;
        this.bossGroup = new NioEventLoopGroup(nBossThread);
        this.workerGroup = new NioEventLoopGroup(nWorkerThread);
    }

    public void start() {

    }
}
