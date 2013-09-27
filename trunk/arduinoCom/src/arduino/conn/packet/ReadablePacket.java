/**
 * 
 */
package arduino.conn.packet;

import java.nio.ByteBuffer;

import arduino.conn.Connection;

/**
 * @author Alisson Oliveira
 *
 */
public interface ReadablePacket extends Packet {
	
	void read(Connection conn, ByteBuffer buf);
	
	void process(Connection conn);

}
