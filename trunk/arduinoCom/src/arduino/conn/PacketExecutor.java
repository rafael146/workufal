/**
 * 
 */
package arduino.conn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import arduino.conn.packet.WritablePacket;

/**
 * @author Alisson Oliveira
 * 
 */
public class PacketExecutor implements PacketListener {

	private static final ByteOrder BYTE_ORDER = ByteOrder.LITTLE_ENDIAN;
	private static final int HEADER_SIZE = 2;
	private int READ_BUFFER_SIZE = 64 * 1024;
	private int WRITE_BUFFER_SIZE = 64 * 1024;
	private int HELPER_BUFFER_COUNT = 20;
	private int HELPER_BUFFER_SIZE = 64 * 1024;

	private final ByteBuffer DIRECT_WRITE_BUFFER;
	private final ByteBuffer WRITE_BUFFER;
	private final ByteBuffer READ_BUFFER;

	private final List<ByteBuffer> bufferPool;
	private final Connection con;

	public PacketExecutor(Connection con) {
		this.con = con;
		DIRECT_WRITE_BUFFER = ByteBuffer.allocateDirect(WRITE_BUFFER_SIZE)
				.order(BYTE_ORDER);
		WRITE_BUFFER = ByteBuffer.wrap(new byte[WRITE_BUFFER_SIZE]).order(
				BYTE_ORDER);
		READ_BUFFER = ByteBuffer.wrap(new byte[READ_BUFFER_SIZE]).order(
				BYTE_ORDER);

		bufferPool = new ArrayList<>();

		for (int i = 0; i < HELPER_BUFFER_COUNT; i++) {
			bufferPool.add(ByteBuffer.wrap(new byte[HELPER_BUFFER_SIZE]).order(BYTE_ORDER));

		}
	}

	final ByteBuffer getPooledBuffer() {
		if (bufferPool.isEmpty())
			return ByteBuffer.wrap(new byte[HELPER_BUFFER_SIZE]).order(BYTE_ORDER);

		return bufferPool.remove(0);
	}

	final void recycleBuffer(final ByteBuffer buf) {
		if (bufferPool.size() < HELPER_BUFFER_COUNT) {
			buf.clear();
			bufferPool.add(buf);
		}
	}

	@Override
	public void onPacketEvent(PacketEvent evt) {
		if (evt == PacketEvent.DATA_RECEIVED) {
			new Executor().start();
		}
	}

	protected class Executor extends Thread {

		@Override
		public void run() {

			if (!prepareBuffer()) {
				return;
			}
			
			DIRECT_WRITE_BUFFER.flip();
		
			try {
				con.write(DIRECT_WRITE_BUFFER);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}

		private boolean prepareBuffer() {
			boolean pending = false;
			DIRECT_WRITE_BUFFER.clear();

			if (con.hasPendingWriteBuffer()) {
				con.movePendingWriteBufferTo(DIRECT_WRITE_BUFFER);
				pending = true;
			}

			if (DIRECT_WRITE_BUFFER.remaining() > 1 && !con.hasPendingWriteBuffer()) {
				WritablePacket packet = null;
				synchronized (con.getSendQueue()) {
					if (!con.getSendQueue().isEmpty())
						packet = con.sendQueue.removeFirst();
				}

				if (packet == null)
					return pending;

				pending = true;

				// put into WriteBuffer
				WRITE_BUFFER.clear();

				// reserve space for the size
				final int headerPos = WRITE_BUFFER.position();
				final int dataPos = headerPos + HEADER_SIZE;
				WRITE_BUFFER.position(dataPos);

				// write content to buffer
				packet.write(con, WRITE_BUFFER);

				// size (inclusive header)
				int dataSize = WRITE_BUFFER.position() - dataPos;

				WRITE_BUFFER.position(headerPos);
				// write header
				WRITE_BUFFER.putShort((short) (dataSize + HEADER_SIZE));
				WRITE_BUFFER.position(dataPos + dataSize);

				WRITE_BUFFER.flip();

				if (DIRECT_WRITE_BUFFER.remaining() >= WRITE_BUFFER.limit())
					DIRECT_WRITE_BUFFER.put(WRITE_BUFFER);
				else {
					con.createWriteBuffer(WRITE_BUFFER);
				}
			}
			return pending;
		}
	}

}
