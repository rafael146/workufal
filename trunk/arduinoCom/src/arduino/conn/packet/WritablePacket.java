package arduino.conn.packet;

import java.nio.ByteBuffer;

import arduino.conn.Connection;

public interface WritablePacket extends Packet {
	
	public void write(Connection conn, ByteBuffer buffer);
	
	public int getOpcode();

}
