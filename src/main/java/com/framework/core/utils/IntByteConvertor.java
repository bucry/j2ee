package com.framework.core.utils;

public class IntByteConvertor {
	
	/**
	 * @param intValue
	 * @return
	 */
	public static byte[] int2Byte(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
		}
		byte[] newByte = new byte[b.length - createNewByte(b)];
		System.arraycopy(b, createNewByte(b), newByte, 0, newByte.length);
		return newByte;
	}
	
	/**
	 * 自定义
	 * @param intValue
	 * @return
	 */
	public static byte[] int2ByteCustom(int intValue) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
		}
		return b;
	}
	
	/**
	 * 自定义
	 * @param intValue
	 * @return
	 */
	public static byte[] int2ByteCustom(int intValue, int byteLength) {
		byte[] b = new byte[byteLength];
		for (int i = 0; i < byteLength; i++) {
			b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
		}
		return b;
	}

	
	/**
	 * 剔除首位为0的字节
	 * @param oldByte
	 * @return
	 */
	private static int createNewByte(byte[] oldByte) {
		int indexValue = 0;
		for (int id = 0; id < oldByte.length; id++) {
			indexValue = id;
			if (oldByte[id] != 0) {
				break;
			}
		}
		return indexValue;
	}
	
	public static int byte2Int(byte[] b) {
		int intValue = 0;
		for (int i = 0; i < b.length; i++) {
			intValue += (b[i] & 0xFF) << (8 * (3 - i));
		}
		return intValue;
	}
}
