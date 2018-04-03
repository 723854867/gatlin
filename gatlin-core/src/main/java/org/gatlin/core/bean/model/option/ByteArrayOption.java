package org.gatlin.core.bean.model.option;

public class ByteArrayOption extends Option<byte[]> {

	private static final long serialVersionUID = 4882995100237849803L;
	
	public ByteArrayOption() {}

	public ByteArrayOption(String key) {
		super(key);
	}
	
	public ByteArrayOption(String key, byte[] defaultValue) {
		super(key, defaultValue);
	}
}
