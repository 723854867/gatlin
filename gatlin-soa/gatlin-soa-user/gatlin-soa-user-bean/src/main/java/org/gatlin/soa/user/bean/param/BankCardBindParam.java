package org.gatlin.soa.user.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.PhoneUtil;
import org.gatlin.util.validate.Mobile;

public class BankCardBindParam extends SoaParam {

	private static final long serialVersionUID = 1086627164934643674L;

	@NotEmpty
	private String city;
	@NotEmpty
	private String bankId;
	@Mobile
	@NotEmpty
	private String mobile;
	@NotEmpty
	private String bankNo;
	private String branch;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String getBankId() {
		return bankId;
	}
	
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	@Override
	public void verify() {
		super.verify();
		this.mobile = PhoneUtil.parseMobile(this.mobile);
	}
}
