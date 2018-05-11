package org.gatlin.skd.chuanglan.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VarMsg implements Serializable {

	private static final long serialVersionUID = 2014520670225438986L;

	private String phone;
	private List<String> vars = new ArrayList<String>();
	
	public VarMsg(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public VarMsg append(String... vars) { 
		for (String var : vars)
			this.vars.add(var);
		return this;
	}
	
	public List<String> getVars() {
		return vars;
	}
}
