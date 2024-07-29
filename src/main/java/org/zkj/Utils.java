package org.zkj;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

public class Utils {
    public static ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg){
        // 获取ByteBuf对象
        ByteBuf buffer = ctx.alloc().buffer();
        // 转换成二进制数据
        byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
        // 填充到ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
