package org.zkj.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.zkj.client.console.ConsoleCommandManager;
import org.zkj.client.console.LoginConsoleCommand;
import org.zkj.client.handler.*;
import org.zkj.codec.PacketDecoder;
import org.zkj.codec.PacketEncoder;
import org.zkj.codec.Spliter;
import org.zkj.handler.IMIdleStateHandler;
import org.zkj.protocal.request.LoginRequestPacket;
import org.zkj.protocal.request.MessageRequestPacket;
import org.zkj.utils.SessionUtil;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY=5;
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new LogoutResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new JoinGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new ListGroupMembersResponseHandler());
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });

        connect(bootstrap,"127.0.0.1", 8000, MAX_RETRY);

//        while (true) {
//            channel.writeAndFlush(new Date() + ": hello world!");
//            Thread.sleep(2000);
//        }
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry){
        bootstrap.connect("127.0.0.1", 8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("连接成功");
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                }else if(retry==0){
                    System.out.println("重连次数用完，放弃连接");
                }else{
                    int order = (MAX_RETRY - retry)+1;
                    System.out.println(new Date() + "连接失败，第"+order+"次重连...");
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), 1, TimeUnit.SECONDS);
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();

        new Thread(() -> {
            while(!Thread.interrupted()){
                if(!SessionUtil.hasLogin(channel)){
                    loginConsoleCommand.exec(scanner, channel);
                }else{
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }
}

class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //创建连接成功后调用Active方法，可以写连接成功后的业务逻辑
        System.out.println(new Date() + ":客户端写出数据");

        ByteBuf buffer = getByteBuf(ctx);

        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buffer = (ByteBuf) msg;
        System.out.println(new Date()+":客户端读到数据："+buffer.toString(Charset.forName("utf-8")));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        // 获取ByteBuf对象
        ByteBuf buffer = ctx.alloc().buffer();
        // 转换成二进制数据
        byte[] bytes = "你好，服务端".getBytes(Charset.forName("utf-8"));
        // 填充到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
