package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.JOIN_GROUP_REQUEST;
@Data
public class JoinGroupRequestPacket extends Packet {
    private String groupId;
    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
