package org.zkj.client.console;

import io.netty.channel.Channel;
import org.zkj.protocal.request.JoinGroupRequestPacket;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket = new JoinGroupRequestPacket();
        System.out.println("输入groupId, 加入群聊：");
        joinGroupRequestPacket.setGroupId(scanner.next());
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
