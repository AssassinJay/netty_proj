package org.zkj.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.zkj.protocal.request.JoinGroupRequestPacket;
import org.zkj.protocal.response.JoinGroupResponsePacket;
import org.zkj.protocal.response.MessageResponsePacket;
import org.zkj.session.Session;
import org.zkj.utils.SessionUtil;
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    public JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        String groupId = joinGroupRequestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.add(channelHandlerContext.channel());

        for (Channel channel : channelGroup) {
            if(channel != channelHandlerContext.channel()){
                String fromUserName = SessionUtil.getSession(channelHandlerContext.channel()).getUserName();
                String fromUserId = SessionUtil.getSession(channelHandlerContext.channel()).getUserId();
                String message = fromUserName+" 加入群聊";
                channel.writeAndFlush(new MessageResponsePacket(message,"server","server"));
            }
        }
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        joinGroupResponsePacket.setGroupId(groupId);
        joinGroupResponsePacket.setSuccess(true);
        channelHandlerContext.channel().writeAndFlush(joinGroupResponsePacket);
    }
}
