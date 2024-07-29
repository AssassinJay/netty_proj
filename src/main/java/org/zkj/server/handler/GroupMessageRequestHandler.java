package org.zkj.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.zkj.protocal.request.GroupMessageRequestPacket;
import org.zkj.protocal.response.GroupMessageResponsePacket;
import org.zkj.session.Session;
import org.zkj.utils.SessionUtil;
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    public GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        String groupId = groupMessageRequestPacket.getGroupId();
        String message = groupMessageRequestPacket.getMessage();
        Session fromUser = SessionUtil.getSession(channelHandlerContext.channel());

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(groupId);
        groupMessageResponsePacket.setFromUser(fromUser);
        groupMessageResponsePacket.setMessage(message);

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
