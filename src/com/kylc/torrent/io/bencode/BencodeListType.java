package com.kylc.torrent.io.bencode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BencodeListType extends BencodeType {
	private final List<BencodeType> elements;
	
	public BencodeListType(List<BencodeType> elements) {
		this.elements = elements;
	}

	public static BencodeType parse(BufferedInputStream input) throws IOException {
		List<BencodeType> elements = new ArrayList<BencodeType>();
		BencodeInputStream benInput = new BencodeInputStream(input, true);
		while(true) {
			BencodeType element = benInput.readNextType();
			if(element == null) {
				break;
			}
			elements.add(element);
		}
		return new BencodeListType(elements);
	}

	@Override
	public void dump(OutputStream output) throws IOException {
		output.write('l');
		for(BencodeType element : elements) {
			element.dump(output);
		}
		output.write('e');
	}

	@Override
	public List<BencodeType> getNativeValue() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[BencodeListType length=" + elements.size() + " elements={");
		for(BencodeType element : elements) {
			builder.append(element.toString() + ", ");
		}
		return builder.append("}]").toString();
	}
}
