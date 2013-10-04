package arduino.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import arduino.conn.packet.WritablePacket;

public abstract class Connection {
	
	private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
	private int WRITE_BUFFER_SIZE = 64*1024;
	private static final int HEADER_SIZE = 2; 
	protected BufferedReader input;
	protected OutputStream output;
	private ByteBuffer readBuffer;
	private final ByteBuffer writeBuffer;

	public Connection() {
		writeBuffer = ByteBuffer.wrap(new byte[WRITE_BUFFER_SIZE]).order(BYTE_ORDER);
		readBuffer = ByteBuffer.wrap(new byte[WRITE_BUFFER_SIZE]).order(BYTE_ORDER);
		
	}

	public synchronized void write(final ByteBuffer buf) throws IOException {
		if (output != null) {
			int l = buf.getShort();
			byte[] array = new byte[l+HEADER_SIZE];
			buf.position(0);
			buf.get(array, 0, l);
			output.write(array);
			output.flush();
		}
	}
	
	public void close() {
		try {
			input.close();
		} catch (IOException e) {

		} finally {
			input = null;
		}

		try {
			output.close();
		} catch (IOException e) {

		} finally {
			output = null;
		}
	}

	public final void sendPacket(WritablePacket packet) throws IOException {
		if (packet == null) {
			return;
		}
		
		writeBuffer.clear();
		// reserve space for the size 
		writeBuffer.position(HEADER_SIZE);
		
		// write content to buffer
		packet.write(this, writeBuffer);
		
		 // size (inclusive header) 
		int dataSize = writeBuffer.position() - HEADER_SIZE; 
		writeBuffer.position(0); 
		// write header 
		writeBuffer.putShort((short) (dataSize)); 
		
		writeBuffer.position(dataSize); 
		
		writeBuffer.flip();
		
		write(writeBuffer);
	}
}
