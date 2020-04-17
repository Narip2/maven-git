package com.test.first_maven;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Function {
	public static String transBytesToStr(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			if (i != 0)
				buffer.append("-");
			// bytes[i]&0xff将有符号byte数值转换为32位有符号整数，其中高24位为0，低8位为byte[i]
			int intMac = bytes[i] & 0xff;
			// toHexString函数将整数类型转换为无符号16进制数字
			String str = Integer.toHexString(intMac);
			if (str.length() != 2) {
				buffer.append("0");
			}
			buffer.append(str);
		}
		return buffer.toString().toUpperCase();
	}

	// 获取计算机MAC地址
	public static String getLocatMac() {
		String result = "";
		try {
			InetAddress adress = InetAddress.getLocalHost();
			NetworkInterface net = NetworkInterface.getByInetAddress(adress);
			byte[] macBytes = net.getHardwareAddress();
			result = transBytesToStr(macBytes);
		} catch (UnknownHostException e) {
			result = "";
			e.printStackTrace();
		} catch (SocketException e) {
			result = "";
			e.printStackTrace();
		} finally {
			return result;
		}
	}
}
