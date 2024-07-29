package org.zkj.client.console;

import io.netty.channel.Channel;
import org.zkj.protocal.request.CreateGroupRequestPacket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USER_ID_SPLITER = ",";
    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        System.out.print("【拉人群聊】输入 userId 列表，userId 之间英文逗号隔开：");

        String userIdList = scanner.next();

        createGroupRequestPacket.setUserIdList(Arrays.asList(userIdList.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
