/**
 * 
 */
package arduino.conn.packet;

import java.nio.ByteBuffer;

import arduino.conn.Connection;

/**
 * @author Alisson Oliveira
 * 
 * 
 * Syntax
 *	LiquidCrystal(rs, enable, d4, d5, d6, d7) 
 *	LiquidCrystal(rs, rw, enable, d4, d5, d6, d7) 
 *	LiquidCrystal(rs, enable, d0, d1, d2, d3, d4, d5, d6, d7) 
 *	LiquidCrystal(rs, rw, enable, d0, d1, d2, d3, d4, d5, d6, d7)
 *	Parameters
 *	rs: the number of the Arduino pin that is connected to the RS pin on the LCD
 *	rw: the number of the Arduino pin that is connected to the RW pin on the LCD (optional)
 *	enable: the number of the Arduino pin that is connected to the enable pin on the LCD
 *	d0, d1, d2, d3, d4, d5, d6, d7: the numbers of the Arduino pins that are connected to the corresponding data pins on the LCD. d0, d1, d2, and d3 are optional; 
 *		if omitted, the LCD will be controlled using only the four data lines (d4, d5, d6, d7).
 *
 *
 * Format
 *  short
 *  byte
 *  byte
 *  byte
 *  String
 *  byte
 *  byte
 *  byte
 *  byte
 *  byte
 *
 */
public class LCDPacket extends AbstractWritablePacket {
	
	String text = "";
	byte line = 0;
	byte column = 0;
	boolean cursor = false;
	boolean blink = false;
	boolean display = true;
	boolean toright = false;
	boolean scroll = true;
	boolean clear = true;
	
	public LCDPacket() {
		
	}
	
	public LCDPacket(String text) {
		this.text = text;
	}
	
	
	public LCDPacket(String text, boolean clear) {
		this.text = text;
		this.clear = clear;
	}
	
	public LCDPacket(String text, byte line, byte column) {
		this.text = text;
		this.line = line;
		this.column = column;
	}
	
	public LCDPacket(String text, byte line, byte column, boolean clear) {
		this.text = text;
		this.line = line;
		this.column = column;
		this.clear = clear;
	}
	
	public void setPosition(byte line, byte column) {
		this.line = line;
		this.column = column;
	}
	
	public void setText(String txt) {
		text = txt;
	}
	
	public void setText(String txt, byte line, byte column) {
		setText(txt);
		this.line = line;
		this.column = column;
	}
	
	public void setCursor(boolean val) {
		cursor = val;
	}
	
	public void setBlink(boolean val) {
		blink = val;
	}
	
	public void setDisplay(boolean val) {
		display = val;
	}
	
	public void setToRight(boolean val) {
		toright = val;
	}
	
	public void setScroll(boolean val) {
		scroll = val;
	}
	
	public void setClear(boolean val) {
		clear = val;
	}
	
	/* (non-Javadoc)
	 * @see arduino.conn.packet.WritablePacket#write(arduino.conn.Connection, java.nio.ByteBuffer)
	 * 
	 */
	@Override
	public void write(Connection conn, ByteBuffer buffer) {
		buffer.putShort(getOpcode());
		buffer.put((byte) (clear ? 1 : 0));
		buffer.put(line);
		buffer.put(column);
		putString(buffer, text);
		buffer.put((byte) (cursor ? 1 : 0));
		buffer.put((byte) (blink ? 1 : 0));
		buffer.put((byte) (display ? 1 : 0));
		buffer.put((byte) (toright ? 1 : 0));
		buffer.put((byte) (scroll ? 1 : 0));

	}

	/* (non-Javadoc)
	 * @see arduino.conn.packet.WritablePacket#getOpcode()
	 */
	@Override
	public short getOpcode() {
		return 0x01;
	}

}