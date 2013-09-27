package test;

import gnu.io.NoSuchPortException;
import arduino.conn.SerialConnection;

public class NewTest {

	public static void main(String[] args) {
		SerialConnection con = null;
		try {
			con = new SerialConnection();
		} catch (NoSuchPortException e) {
			System.out.println("No Device Connected");
			System.exit(0);
		}

	}

}
