/**
 * 
 */
package arduino.conn;

import java.nio.ByteBuffer;

/**
 * @author Alisson Oliveira
 *
 */
public interface ReadablePacket<T extends Connection> extends Packet<T> {
	
	void read(T conn, ByteBuffer buf);
	
	void process(T conn);

}
