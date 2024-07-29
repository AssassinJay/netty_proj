package org.zkj.protocal.request;

import lombok.Data;
import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
