package com.chris.allinone.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chrischen
 * @date 2025/6/23
 * @desc EchoClientHandler
 */
@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("Message from Echo Server: {}", msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Client channelActive");
        byte[] bytes = new byte[1024];
        while (true) {
            int read = System.in.read(bytes);
            if (read == -1) {
                break;
            } else {
                ctx.writeAndFlush(Unpooled.copiedBuffer(
                        new String(bytes, 0, read),CharsetUtil.UTF_8));
            }
        }
    }
}
