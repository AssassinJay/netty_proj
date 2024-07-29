package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;

import java.util.List;

import static org.zkj.protocal.command.Command.CREATE_GROUP_RESPONSE;
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_RESPONSE;
    }
}
