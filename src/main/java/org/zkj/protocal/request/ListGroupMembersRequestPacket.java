package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.LIST_GROUP_MEMBERS_REQUEST;
@Data
public class ListGroupMembersRequestPacket extends Packet {

    private String groupId;
    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
