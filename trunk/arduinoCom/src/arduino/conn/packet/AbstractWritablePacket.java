/**
 * 
 */
package arduino.conn.packet;

import java.nio.ByteBuffer;

/**
 * @author Alisson Oliveira
 *
 */
public abstract class AbstractWritablePacket implements WritablePacket {
	
	protected void putString(ByteBuffer buf, String str) {
		if(str != null) {
			for(int i = 0; i < str.length(); i++) {
				buf.put((byte)str.charAt(i));
			}
		}
		buf.put((byte)0);
	}
	
	@Override
	public String toString() {
		return "Writable Packet: " + getClass().getSimpleName(); 
	}

}
