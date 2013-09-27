package test;

import java.nio.ByteBuffer;

public class Test {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.putInt(4);
		buf.putInt(5);
		buf.putShort((short)2);
		buf.flip();
		System.out.println();
		System.out.println(buf.getInt());
		System.out.println(buf.getInt());
		System.out.println(buf.getShort());
		buf.flip();
		buf.putInt(4);
		buf.putInt(5);
		buf.flip();
		System.out.println(buf.getInt());
		System.out.println(buf.getInt());		
	}

}
