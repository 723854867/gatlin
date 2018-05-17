package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class LogBankCardBind implements Identifiable<String> {

	private static final long serialVersionUID = 4659851172140139451L;
	
	@Id
	private String id;
	private String params;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	@Override
	public String key() {
		return this.id;
	}
}
