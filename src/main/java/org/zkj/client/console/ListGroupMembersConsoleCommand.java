package org.zkj.client.console;

import io.netty.channel.Channel;
import org.zkj.protocal.request.ListGroupMembersRequestPacket;

import java.util.Scanner;

public class ListGroupMembersConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMembersRequestPacket listGroupMembersRequestPacket = new ListGroupMembersRequestPacket();
        System.out.println("输入groupId, 查看群成员：");
        listGroupMembersRequestPacket.setGroupId(scanner.next());
        channel.writeAndFlush(listGroupMembersRequestPacket);
    }
}
