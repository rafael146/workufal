package arduino.conn.packet;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import arduino.conn.Connection;

public class GeneralPacket extends AbstractWritablePacket {
	
	Map<Integer, Integer> portas = new HashMap<>();
	
	public void add(int porta, int value) {
		portas.put(porta, value);
	}
	
	public void remove(int porta) {
		portas.remove(porta);
	}
	
	public void add(Map<Integer, Integer> portas) {
		this.portas.putAll(portas);
	}

	@Override
	public void write(Connection conn, ByteBuffer buffer) {
		buffer.putShort(getOpcode());
		byte length = (byte)portas.size();
		buffer.put(length);
		for(int p : portas.keySet()) {
			buffer.put((byte) p);
			int v = portas.get(p);
			buffer.put((byte)v);
		}
		
	}

	@Override
	public short getOpcode() {
		return 0x00;
	}

}
