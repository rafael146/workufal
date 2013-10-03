package test;

import java.nio.ByteBuffer;

public class Test {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.putInt(3);
		buf.putInt(5);
		buf.putShort((short)2);
		byte array[] = new byte[buf.position()];
		System.out.println(array.length);
		buf.flip();
		buf.get(array);
		for(byte b : array) {
			System.out.println(b);
		}
		buf.position(0);
		System.out.println("DAsd");
		System.out.println(buf.getInt());
//		
//		System.out.println();
//		System.out.println(buf.getInt());
//		System.out.println(buf.getInt());
//		System.out.println(buf.getShort());
//		buf.flip();
//		buf.putInt(4);
//		buf.putInt(5);
//		buf.flip();
//		System.out.println(buf.getInt());
//		System.out.println(buf.getInt());		
	}

}
