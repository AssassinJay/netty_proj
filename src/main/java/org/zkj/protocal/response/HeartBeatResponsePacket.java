package org.zkj.protocal.response;

import org.zkj.protocal.Packet;

import static org.zkj.protocal.command.Command.HEART_BEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_RESPONSE;
    }
}
