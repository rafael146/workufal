/**
 * 
 */
package arduino.conn;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * @author Alisson Oliveira
 * 
 */
public class SerialConnection extends Connection  implements SerialPortEventListener {

	private static final String PORT_NAMES[] = {
		 	"/dev/tty.usbserial-A9007UX1", // Mac  OS  X
			"/dev/ttyACM0", // Linux
			"COM3", // Windows
	};

	private SerialPort serialPort;
	private Map<String, CommPortIdentifier> serialPorts = new HashMap<>();

	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public SerialConnection() throws NoSuchPortException {
		findPorts();
	}
	
	public SerialPort getSerialPort() {
		return serialPort;
	}

	public SerialConnection(String portname) throws NoSuchPortException {
		connect(findPort(portname));
	}

	public void connect(CommPortIdentifier portId) {
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			
			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			System.err.println(e.toString());
		}

	}
	
	public boolean isConnected() {
		return serialPort != null; 
	}

	protected CommPortIdentifier findPort(String portName)
			throws NoSuchPortException {
		return CommPortIdentifier.getPortIdentifier(portName);
	}

	private void findPorts() throws NoSuchPortException {
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					if(serialPort == null)
						connect(currPortId	);
					serialPorts.put(portName, currPortId);
				}
			}
		}
		if (serialPorts.size() == 0) {
			throw new NoSuchPortException();
		}
	}

	@Override
	public void close() {
		super.close();
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
		serialPort = null;
	}
	
	public Map<String, CommPortIdentifier> getSerialPorts() {
		return serialPorts;
	}

	@Override
	public void serialEvent(SerialPortEvent evt) {
		if (evt.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine=input.readLine();
				System.out.println(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}

}
