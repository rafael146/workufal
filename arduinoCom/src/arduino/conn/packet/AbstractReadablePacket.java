/**
 * 
 */
package arduino.conn.packet;

import java.nio.ByteBuffer;

/**
 * @author Alisson Oliveira
 *
 */
public abstract class AbstractReadablePacket implements ReadablePacket {
	
	protected String readString(ByteBuffer buf) {
		char ch;
		StringBuilder sb = new StringBuilder();
		while ((ch = buf.getChar()) != 0) {
			sb.append(ch);
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "Readable Packet " + getClass().getSimpleName();
	}

}
