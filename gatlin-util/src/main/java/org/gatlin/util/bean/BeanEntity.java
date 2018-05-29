package org.gatlin.util.bean;

import java.io.Serializable;
import java.util.List;

public class BeanEntity implements Serializable {

	private static final long serialVersionUID = -7773660899686454557L;

	private List<BeanCol> cols;
	
	public BeanEntity() {}
	
	public BeanEntity(List<BeanCol> cols) {
		this.cols = cols;
	}
	
	public List<BeanCol> getCols() {
		return cols;
	}
	
	public void setCols(List<BeanCol> cols) {
		this.cols = cols;
	}
}
