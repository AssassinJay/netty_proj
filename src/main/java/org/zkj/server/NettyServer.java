package org.zkj.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.zkj.Utils;
import org.zkj.client.handler.LogoutResponseHandler;
import org.zkj.codec.PacketCodecHandler;
import org.zkj.codec.PacketDecoder;
import org.zkj.codec.PacketEncoder;
import org.zkj.codec.Spliter;
import org.zkj.handler.IMIdleStateHandler;
import org.zkj.server.handler.*;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author 闪电侠
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        ch.pipeline().addLast(HeartBeatRequestHandler.INSTANCE);
                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(IMRequestHandler.INSTANCE);
                    }
                });
        bind(serverBootstrap,8000);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println(port+"端口绑定成功");
                }else{
                    System.out.println(port+"端口绑定失败");
                    bind(serverBootstrap,port+1);
                }
            }
        });
    }
}

class FirstServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date()+":服务端读到数据："+buffer.toString(Charset.forName("utf-8")));

        System.out.println(new Date()+":服务端写出数据");
        ByteBuf buf = Utils.getByteBuf(ctx,"你好，客户端");
        ctx.writeAndFlush(buf);
    }
}
