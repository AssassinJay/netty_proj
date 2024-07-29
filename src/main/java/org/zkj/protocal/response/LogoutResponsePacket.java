package org.zkj.protocal.response;

import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.LOGOUT_RESPONSE;

public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
