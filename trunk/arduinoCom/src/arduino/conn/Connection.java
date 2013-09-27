package arduino.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import arduino.conn.packet.WritablePacket;

public abstract class Connection {

	protected BufferedReader input;
	
	protected OutputStream output;
	
	private ByteBuffer readBuffer;
	
	private ByteBuffer primaryWriteBuffer;
	
	private ByteBuffer secondaryWriteBuffer;
	
	protected PacketQueue<WritablePacket> sendQueue;
	
	private PacketExecutor executor;
	
	
	public Connection() {
		sendQueue = new PacketQueue<>();
		executor = new PacketExecutor(this);
		sendQueue.addListener(executor);
	}
	
	final boolean hasPendingWriteBuffer()
	{
		return primaryWriteBuffer != null;
	}
	
	final void movePendingWriteBufferTo(final ByteBuffer dest)
	{
		primaryWriteBuffer.flip();
		dest.put(primaryWriteBuffer);
		//_selectorThread.recycleBuffer(_primaryWriteBuffer);
		primaryWriteBuffer = secondaryWriteBuffer;
		secondaryWriteBuffer = null;
	}
	
	final void createWriteBuffer(final ByteBuffer buf)
	{
		if (primaryWriteBuffer == null)
		{
			primaryWriteBuffer = executor.getPooledBuffer();
			primaryWriteBuffer.put(buf);
		}
		else
		{
			final ByteBuffer temp = executor.getPooledBuffer();
			temp.put(buf);
			
			final int remaining = temp.remaining();
			primaryWriteBuffer.flip();
			final int limit = primaryWriteBuffer.limit();
			
			if (remaining >= primaryWriteBuffer.remaining())
			{
				temp.put(primaryWriteBuffer);
				executor.recycleBuffer(primaryWriteBuffer);
				primaryWriteBuffer = temp;
			}
			else
			{
				primaryWriteBuffer.limit(remaining);
				temp.put(primaryWriteBuffer);
				primaryWriteBuffer.limit(limit);
				primaryWriteBuffer.compact();
				secondaryWriteBuffer = primaryWriteBuffer;
				primaryWriteBuffer = temp;
			}
		}
	}
	
	final synchronized boolean write(final ByteBuffer buf) throws IOException
	{
		if(output !=null) {
			output.write(buf.array());
			return true;
		}
		return false;
	}
	
	public PacketQueue<WritablePacket> getSendQueue() {
		return sendQueue;
	}
	
	public void close() {
		try {
			input.close();
		} catch(IOException e) {
			
		} finally {
			input = null;
		}
		
		try {
			output.close();
		} catch(IOException e) {
			
		} finally {
			output = null;
		}
	}


	public final void sendPacket(WritablePacket packet) {
		sendQueue.addLast(packet);
	}
}
