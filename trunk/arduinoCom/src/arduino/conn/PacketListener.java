package arduino.conn;

public interface PacketListener {
	
	public static enum PacketEvent {
		DATA_RECEIVED
	};
	
	
	void onPacketEvent(PacketEvent evt);

}
