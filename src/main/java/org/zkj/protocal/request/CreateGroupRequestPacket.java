package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;
import org.zkj.protocal.PacketCodeC;

import java.util.List;

import static org.zkj.protocal.command.Command.CREATE_GROUP_REQUEST;
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
