package org.gatlin.sdk.sinapay.bean.model;

import java.io.Serializable;

public abstract class PayMethod implements Serializable {

	private static final long serialVersionUID = -3728736607890553560L;

	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public abstract String toString();
}
