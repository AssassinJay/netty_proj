package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {

        return JOIN_GROUP_RESPONSE;
    }
}
