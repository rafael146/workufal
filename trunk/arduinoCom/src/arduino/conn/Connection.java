package arduino.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import arduino.conn.packet.WritablePacket;

public abstract class Connection {
	
	private static final int HEADER_SIZE = 2;
	private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
	private int WRITE_BUFFER_SIZE = 64 * 1024;

	protected BufferedReader input;
	protected OutputStream output;
	private ByteBuffer readBuffer;
	private final ByteBuffer writeBuffer;

	public Connection() {
		writeBuffer = ByteBuffer.allocateDirect(WRITE_BUFFER_SIZE).order(BYTE_ORDER);
		readBuffer = ByteBuffer.wrap(new byte[WRITE_BUFFER_SIZE]).order(BYTE_ORDER);
		
	}

	protected synchronized void write(final ByteBuffer buf) throws IOException {
		if (output != null) {
			output.write(buf.array());
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
		final int headerPos = writeBuffer.position();
		final int dataPos = headerPos + HEADER_SIZE;
		writeBuffer.position(dataPos);

		// write content to buffer
		packet.write(this, writeBuffer);

		// size (inclusive header)
		int dataSize = writeBuffer.position() - dataPos;

		writeBuffer.position(headerPos);
		// write header
		writeBuffer.putShort((short) (dataSize + HEADER_SIZE));
		writeBuffer.position(dataPos + dataSize);

		writeBuffer.flip();
		write(writeBuffer);

	}
}
