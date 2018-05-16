package org.gatlin.soa.config.bean.param;

import java.util.Set;

import org.gatlin.soa.bean.param.SoaParam;

public class DistrictsParam extends SoaParam {
	
	private static final long serialVersionUID = 5352573769257299540L;
	
	private Set<Integer> codes;
	
	public Set<Integer> getCodes() {
		return codes;
	}
	
	public void setCodes(Set<Integer> codes) {
		this.codes = codes;
	}
}
