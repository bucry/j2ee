package com.framework.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ObjectTypeConvert {

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}

	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}

	
	public static byte[] converIn2Byte(int res) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeByte(4);
			dos.writeByte(1);
			dos.writeByte(1);
			dos.writeShort(217);
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	
	public static int coverByte2Int(byte[] res) {
		ByteArrayInputStream bais = new ByteArrayInputStream(res);
		DataInputStream dis = new DataInputStream(bais);
		StringBuilder sb = new StringBuilder();
		try {
			for (int index = 0; index < 3; index++) {
				sb.append(dis.readByte());
			}
			sb.append(dis.readShort());
			dis.close();
			return Integer.parseInt(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}
}
