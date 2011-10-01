package com.kylc.torrent.io.bencode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BencodeIntegerType extends BencodeType {
	private final int value;
	
	public BencodeIntegerType(int value) {
		this.value = value;
	}

	public static BencodeType parse(BufferedInputStream input) throws IOException {
		int value = 0;
		char read;
		while((read = (char) input.read()) != 'e') {
			value = (value * 10) + Character.getNumericValue(read);
		}
		return new BencodeIntegerType(value);
	}

	@Override
	public void dump(OutputStream output) throws IOException {
		output.write('i');
		output.write(Integer.toString(value).getBytes());
		output.write('e');
	}
	
	@Override
	public Integer getNativeValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "[BencodeIntegerType=" + value + "]";
	}
}
