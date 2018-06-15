package org.gatlin.util.io.excel;

import java.io.Serializable;
import java.util.List;

public class ExcelEntity implements Serializable {

	private static final long serialVersionUID = -7773660899686454557L;

	private List<ExcelCol> cols;
	
	public ExcelEntity() {}
	
	public ExcelEntity(List<ExcelCol> cols) {
		this.cols = cols;
	}
	
	public List<ExcelCol> getCols() {
		return cols;
	}
	
	public void setCols(List<ExcelCol> cols) {
		this.cols = cols;
	}
}
