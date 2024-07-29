package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;
import org.zkj.session.Session;

import java.util.List;

import static org.zkj.protocal.command.Command.LIST_GROUP_MEMBERS_RESPONSE;
@Data
public class ListGroupMembersResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
