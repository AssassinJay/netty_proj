package org.zkj.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.zkj.protocal.response.ListGroupMembersResponsePacket;

public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ListGroupMembersResponsePacket listGroupMembersResponsePacket) throws Exception {
        System.out.println("群[" + listGroupMembersResponsePacket.getGroupId() + "]中的人包括：" + listGroupMembersResponsePacket.getSessionList());
    }
}
