package arduino.conn;

import java.nio.ByteBuffer;

public interface WritablePacket<T extends Connection> extends Packet<T> {
	
	public void write(T conn, ByteBuffer buffer);
	
	public int getOpcode();

}
