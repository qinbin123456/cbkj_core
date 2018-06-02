package com.example.cbkj_core.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
	private MD5Util(){
	}

	public static byte[] md5Bytes(String text) {
		if (null == text || "".equals(text)) {
			return new byte[0];
		}

		MessageDigest msgDigest = null;
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("System doesn't support MD5 algorithm.");
		}

		msgDigest.update(text.getBytes());
		byte[] bytes = msgDigest.digest();
		return bytes;
	}


	public static String md5(String text, boolean isReturnRaw) {
		if (null == text || "".equals(text)) {
			return text;
		}
		byte[] bytes = md5Bytes(text);
		if (isReturnRaw) {
			return new String(bytes);
		}

		String md5Str = new String();
		byte tb;
		char low;
		char high;
		char tmpChar;

		for (int i = 0; i < bytes.length; i++) {
			tb = bytes[i];

			tmpChar = (char) ((tb >>> 4) & 0x000f);
			if (tmpChar >= 10) {
				high = (char) (('a' + tmpChar) - 10);
			} else {
				high = (char) ('0' + tmpChar);
			}
			md5Str += high;

			tmpChar = (char) (tb & 0x000f);
			if (tmpChar >= 10) {
				low = (char) (('a' + tmpChar) - 10);
			} else {
				low = (char) ('0' + tmpChar);
			}
			md5Str += low;
		}

		return md5Str;
	}
	public static String encode(String password) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		char[] charArray = password.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}

			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String md5(String text) {
		return md5(text, false);
	}

	public static void main(String[] args){
		for(int i = 1536;i<3041;i++){
			Integer e = i;
			String xe = e.toHexString(e);
			System.out.println(".xe-"+xe+":before { content: \"\\e"+xe+"\"; }");
		}
	}

}
