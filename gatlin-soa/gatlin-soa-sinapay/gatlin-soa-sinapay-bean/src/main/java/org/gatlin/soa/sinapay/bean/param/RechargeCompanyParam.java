package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.Min;

import org.gatlin.soa.bean.enums.TargetType;

/**
 * 企业充值
 * 
 * @author lynn
 */
public class RechargeCompanyParam extends RechargeParam {

	private static final long serialVersionUID = -2284951160155633004L;

	@Min(1)
	private int companyId;
	
	public int getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Override
	public void verify() {
		super.verify();
		setRechargee(Long.valueOf(companyId));
		setRechargeeType(TargetType.COMPANY);
	}
}
