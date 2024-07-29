package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;
import org.zkj.session.Session;

import static org.zkj.protocal.command.Command.GROUP_MESSAGE_RESPONSE;
@Data
public class GroupMessageResponsePacket extends Packet {
    String fromGroupId;
    Session fromUser;
    String message;
    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
