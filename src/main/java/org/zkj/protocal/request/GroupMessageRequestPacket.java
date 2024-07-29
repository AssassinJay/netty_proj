package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.GROUP_MESSAGE_REQUEST;
@Data
public class GroupMessageRequestPacket extends Packet {

    String groupId;
    String message;

    public GroupMessageRequestPacket() {
    }

    public GroupMessageRequestPacket(String groupId, String message) {
        this.groupId = groupId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
