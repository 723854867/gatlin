package org.gatlin.soa.sinapay.bean.param;

import javax.validation.constraints.NotEmpty;

import org.gatlin.soa.bean.param.SoaLidParam;
import org.gatlin.util.lang.StringUtil;
import org.gatlin.util.validate.Mobile;

public class CompanyApplyParam extends SoaLidParam {

	private static final long serialVersionUID = -2406361969638389390L;

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
	// 文件摘要
	private String digest;
	// 文件名
	private String filename;
	@NotEmpty
	private String certEffectDate;
	@NotEmpty
	private String certInvalidDate;

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

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
	
	public String getCertEffectDate() {
		return certEffectDate;
	}

	public void setCertEffectDate(String certEffectDate) {
		this.certEffectDate = certEffectDate;
	}

	public String getCertInvalidDate() {
		return certInvalidDate;
	}

	public void setCertInvalidDate(String certInvalidDate) {
		this.certInvalidDate = certInvalidDate;
	}

	@Override
	public void verify() {
		super.verify();
		if (null == branch)
			this.branch = StringUtil.EMPTY;
	}
}
