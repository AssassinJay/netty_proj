package org.zkj.protocal.request;

import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.LOGOUT_REQUEST;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
