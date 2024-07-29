package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.MESSAGE_RESPONSE;
@Data
public class MessageResponsePacket extends Packet {

    private String message;
    private String fromUserId;
    private String fromUserName;

    public MessageResponsePacket(String message, String fromUserId, String fromUserName) {
        this.message = message;
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
    }

    public MessageResponsePacket() {
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
