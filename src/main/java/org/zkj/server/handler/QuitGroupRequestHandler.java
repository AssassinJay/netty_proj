package org.zkj.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.zkj.protocal.request.QuitGroupRequestPacket;
import org.zkj.protocal.response.MessageResponsePacket;
import org.zkj.protocal.response.QuitGroupResponsePacket;
import org.zkj.utils.SessionUtil;
@ChannelHandler.Sharable
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    public QuitGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        String groupId = quitGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channelHandlerContext.channel());

        for (Channel channel : channelGroup) {
            if(channel != channelHandlerContext.channel()){
                String fromUserName = SessionUtil.getSession(channelHandlerContext.channel()).getUserName();
                String fromUserId = SessionUtil.getSession(channelHandlerContext.channel()).getUserId();
                String message = fromUserName+" 退出群聊";
                channel.writeAndFlush(new MessageResponsePacket(message,"server","server"));
            }
        }

        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(groupId);
        quitGroupResponsePacket.setSuccess(true);

        channelHandlerContext.channel().writeAndFlush(quitGroupResponsePacket);

        if (channelGroup.size() == 0) {
            SessionUtil.removeChannelGroup(groupId);
            String message = "群聊:"+groupId+"已删除";
            channelHandlerContext.channel().writeAndFlush(new MessageResponsePacket(message,"server","server"));
        }
    }
}
