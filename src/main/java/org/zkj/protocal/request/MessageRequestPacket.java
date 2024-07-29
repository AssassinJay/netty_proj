package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.MESSAGE_REQUEST;
@Data
public class MessageRequestPacket extends Packet {

    private String message;
    private String toUserId;

    public MessageRequestPacket(String message, String toUserId) {
        this.message = message;
        this.toUserId = toUserId;
    }

    public MessageRequestPacket() {
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
