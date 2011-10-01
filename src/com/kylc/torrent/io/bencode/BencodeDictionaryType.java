package com.kylc.torrent.io.bencode;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class BencodeDictionaryType extends BencodeType {
	private final List<BencodeDictionaryElement> elements;
	
	public BencodeDictionaryType(List<BencodeDictionaryElement> elements) {
		this.elements = elements;
	}

	public static BencodeType parse(BufferedInputStream input) throws IOException {
		List<BencodeDictionaryElement> elements = new ArrayList<BencodeDictionaryElement>();
		BencodeInputStream benInput = new BencodeInputStream(input, true);
		while(true) {
			BencodeByteStringType key = (BencodeByteStringType) benInput.readNextType();
			if(key == null) {
				break;
			}
			BencodeType value = benInput.readNextType();
			elements.add(new BencodeDictionaryElement(key, value));
		}
		return new BencodeDictionaryType(elements);
	}

	@Override
	public void dump(OutputStream output) throws IOException {
		output.write('d');
		for(BencodeDictionaryElement entry : elements) {
			entry.getKey().dump(output);
			entry.getValue().dump(output);
		}
		output.write('e');
	}
	
	@Override
	public List<BencodeDictionaryElement> getNativeValue() {
		return elements;
	}
	
	public void reverse() {
		
	}
	
	public BencodeType lookup(String key) {
		for(BencodeDictionaryElement element : elements) {
			if(element.getKey().getNativeValue().equals(key)) {
				return element.getValue();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[BencodeDictionaryType length=" + elements.size() + " elements={");
		for(BencodeDictionaryElement entry : elements) {
			builder.append(entry.toString() + ", ");
		}
		return builder.append("}]").toString();
	}
	
	public static class BencodeDictionaryElement {
		private final BencodeByteStringType key;
		private final BencodeType value;
		
		public BencodeDictionaryElement(BencodeByteStringType key, BencodeType value) {
			this.key = key;
			this.value = value;
		}

		public BencodeByteStringType getKey() {
			return key;
		}

		public BencodeType getValue() {
			return value;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("[BencodeDictionaryElement key=").append(key.toString())
				.append(" value=").append(value.toString()).append("]");
			return builder.append("}]").toString();
		}
	}
}