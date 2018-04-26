package org.gatlin.soa.resource.bean.enums;

public enum DefaultUploadType implements UploadType {

	BANNER(1);
	
	private int mark;
	
	private DefaultUploadType(int mark) {
		this.mark = mark;
	}
	
	@Override
	public int key() {
		return mark;
	}
	
	@Override
	public String directory() {
		return name().toLowerCase();
	}
	
	public static final UploadType match(int type) {
		for (DefaultUploadType temp : DefaultUploadType.values()) {
			if (temp.mark == type)
				return temp;
		}
		return null;
	}
}
