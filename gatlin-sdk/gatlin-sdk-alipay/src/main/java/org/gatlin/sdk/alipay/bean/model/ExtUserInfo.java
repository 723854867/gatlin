package org.gatlin.sdk.alipay.bean.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ExtUserInfo implements Serializable {

	private static final long serialVersionUID = -4324716642589934095L;

	// 可选：姓名 - need_check_info=T时该参数才有效
	private String name;
	// 可选：手机号
	private String mobile;
	// 可选：身份证：IDENTITY_CARD、护照：PASSPORT、军官证：OFFICER_CARD、士兵证：SOLDIER_CARD、户口本：HOKOU等。如有其它类型需要支持，请与蚂蚁金服工作人员联系。 need_check_info=T时该参数才有效
	@SerializedName("cert_type")
	private String certType;
	// 可选：证件号 - ：need_check_info=T时该参数才有效
	@SerializedName("cert_no")
	private String certNo;
	// 可选：允许的最小买家年龄，买家年龄必须大于等于所传数值。1. need_check_info=T时该参数才有效 2. min_age为整数，必须大于等于0
	@SerializedName("min_age")
	private String minAge;
	// 可选：是否强制校验付款人身份信息 - T:强制校验，F：不强制
	@SerializedName("fix_buyer")
	private String fixBuyer;
	// 可选：是否强制校验身份信息 - T:强制校验，F：不强制
	@SerializedName("need_check_info")
	private String needCheckInfo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMinAge() {
		return minAge;
	}

	public void setMinAge(String minAge) {
		this.minAge = minAge;
	}

	public String getFixBuyer() {
		return fixBuyer;
	}

	public void setFixBuyer(String fixBuyer) {
		this.fixBuyer = fixBuyer;
	}

	public String getNeedCheckInfo() {
		return needCheckInfo;
	}

	public void setNeedCheckInfo(String needCheckInfo) {
		this.needCheckInfo = needCheckInfo;
	}
}
