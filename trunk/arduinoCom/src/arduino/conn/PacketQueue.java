/**
 * @author Leonardo Maxwell
 * @author Alisson Oliveira
 *
 */

package arduino.conn;

import arduino.conn.PacketListener.PacketEvent;

public class PacketQueue<E> {

	private final PacketQueueNode start = new PacketQueueNode();
	
	private PacketQueueNode end = new PacketQueueNode();
	
	private final PacketQueueNodeBuf buf = new PacketQueueNodeBuf();
	
	private PacketListener listener;

	public final boolean isEmpty() {
		return start.next == end;
	}

	public final void addLast(final E e) {
		final PacketQueueNode node = buf.removeFirst();
		end.data = e;
		end.next = node;
		end = node;
		if(listener != null)
			listener.onPacketEvent(PacketEvent.DATA_RECEIVED);

	}

	public final E removeFirst() {
		final PacketQueueNode old = start.next;
		final E value = old.data;
		start.next = old.next;
		buf.addLast(old);
		return value;
	}

	public final void clear() {
		start.next = end;
	}
	
	public void addListener(PacketListener ls) {
		listener = ls;
	}

	protected final class PacketQueueNode {
		protected E data;
		protected PacketQueueNode next;

	}

	private final class PacketQueueNodeBuf {
		private final PacketQueueNode start = new PacketQueueNode();
		private PacketQueueNode end = new PacketQueueNode();

		PacketQueueNodeBuf() {
			start.next = end;
		}

		final void addLast(final PacketQueueNode node) {
			node.next = null;
			node.data = null;
			end.next = node;
			end = node;
		}

		final PacketQueueNode removeFirst() {
			if (start.next == end)
				return new PacketQueueNode();

			final PacketQueueNode aux = start.next;
			start.next = aux.next;
			return aux;
		}

	}
}
