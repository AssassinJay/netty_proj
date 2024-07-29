package org.zkj.protocal.request;

import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.HEART_BEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_REQUEST;
    }
}
