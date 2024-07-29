package org.zkj.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.zkj.protocal.request.CreateGroupRequestPacket;
import org.zkj.protocal.response.CreateGroupResponsePacket;
import org.zkj.utils.IDUtil;
import org.zkj.utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    public CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIdList = createGroupRequestPacket.getUserIdList();

        List<String> userNameList = new ArrayList<>();

        ChannelGroup channelgroup = new DefaultChannelGroup(channelHandlerContext.executor());

        for(String userId:userIdList){
            Channel channel = SessionUtil.getChannel(userId);
            if(channel!=null){
                channelgroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserNameList(userNameList);
        createGroupResponsePacket.setGroupId(IDUtil.randomUserId());

        channelgroup.writeAndFlush(createGroupResponsePacket);
        System.out.print("群创建成功，id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        SessionUtil.bindChannelGroup(createGroupResponsePacket.getGroupId(), channelgroup);
    }
}
