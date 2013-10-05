package test;

import java.io.IOException;

import gnu.io.NoSuchPortException;
import arduino.conn.Connection;
import arduino.conn.SerialConnection;
import arduino.conn.packet.LCDPacket;

public class NewTest {

	public static void main(String[] args) {
		SerialConnection con = null;
		try {
			con = new SerialConnection();
		} catch (NoSuchPortException e) {
			System.out.println("No Device Connected");
			System.exit(0);
		}
		System.out.println("Waiting to estabilish the connection");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Device connected " + con.getSerialPort().getName());
		(new PacketSender(con)).start();
		while(true){}
		
//		try {
//			ByteBuffer b = ByteBuffer.allocate(4);
//			b.putShort((short)1);
//			b.put((byte) 2);
//			b.flip();
//			con.write(b);
//			
//		} catch (IOException e) {
//			System.out.println("couldn't send packet");
//			e.printStackTrace();
//		}
		
	}
	
	static class PacketSender extends Thread {
		Connection conn;
		
		public PacketSender(Connection con) {
			this.conn = con;

		}
		
		@Override
		public void run() {
			LCDPacket packet = new LCDPacket("Alisson Oliveira Valerio");
			try {
				conn.sendPacket(packet);
			} catch (IOException e) {
				System.out.println("Error enviando packet");
				e.printStackTrace();
			}
		}
	}

}
