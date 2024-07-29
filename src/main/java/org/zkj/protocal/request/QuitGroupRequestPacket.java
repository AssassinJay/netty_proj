package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.QUIT_GROUP_REQUEST;
@Data
public class QuitGroupRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
