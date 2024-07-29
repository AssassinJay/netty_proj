package org.zkj.protocal.response;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;
    private String userName;
    private String userId;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
