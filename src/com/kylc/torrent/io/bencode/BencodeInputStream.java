package com.kylc.torrent.io.bencode;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class BencodeInputStream extends FilterInputStream {
	private static final Logger logger = Logger.getLogger(BencodeInputStream.class.getName());
	
	private final BufferedInputStream input;
	
	public BencodeInputStream(InputStream in) {
		this(in, false);
	}
	
	public BencodeInputStream(InputStream in, boolean subStream) {
		super(in);
		if(!subStream) {
			this.input = new BufferedInputStream(in);
		} else {
			this.input = (BufferedInputStream) in;
		}
	}
	
	public BencodeType readNextType() throws IOException {
		input.mark(0);
		char ident = (char) input.read();
		
		BencodeType type = null;
		if(ident == 'i') { // Integer
			type = BencodeIntegerType.parse(input);
		} else if(ident == 'l') { // List
			type = BencodeListType.parse(input);
		} else if(ident == 'd') { // Dictionary
			type = BencodeDictionaryType.parse(input);
		} else if(Character.isDigit(ident)) { // ByteString
			input.reset();
			type = BencodeByteStringType.parse(input);
		} else if(ident == 'e') { // End
			return null;
		}
		logger.fine(type.toString());
		
		return type;
	}
}
