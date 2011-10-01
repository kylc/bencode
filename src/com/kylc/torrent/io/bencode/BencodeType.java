package com.kylc.torrent.io.bencode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class BencodeType {
	public static BencodeType parse(BufferedInputStream input) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	public void dump(OutputStream output) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	public abstract Object getNativeValue();
}
