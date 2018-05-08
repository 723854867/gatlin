package org.gatlin.sdk.alipay.bean.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class AppPayModel implements Serializable {

	private static final long serialVersionUID = -3424658616065657163L;

	// 可选：系统商编号，该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
	@SerializedName("sys_service_provider_id")
	private String sysServiceProviderId;
	// 可选：是否发起实名校验 - T：发起,F：不发起
	private String needBuyerRealnamed;
	// 可选：账务备注(该字段显示在离线账单的账务备注中)
	@SerializedName("TRANS_MEMO")
	private String transMemo;
	// 可选：花呗分期数（目前仅支持3、6、12）。使用该参数需要仔细阅读"花呗分期接入文档"
	@SerializedName("hb_fq_num")
	private String hbFqNum;
	// 可选：卖家承担收费比例，商家承担手续费传入100，用户承担手续费传入0，仅支持传入100、0两种，其他比例暂不支持。使用该参数需要仔细阅读"花呗分期接入文档"
	@SerializedName("hb_fq_seller_percent")
	private String hbFqSellerPercent;

	public String getSysServiceProviderId() {
		return sysServiceProviderId;
	}

	public void setSysServiceProviderId(String sysServiceProviderId) {
		this.sysServiceProviderId = sysServiceProviderId;
	}

	public String getNeedBuyerRealnamed() {
		return needBuyerRealnamed;
	}

	public void setNeedBuyerRealnamed(String needBuyerRealnamed) {
		this.needBuyerRealnamed = needBuyerRealnamed;
	}

	public String getTransMemo() {
		return transMemo;
	}

	public void setTransMemo(String transMemo) {
		this.transMemo = transMemo;
	}

	public String getHbFqNum() {
		return hbFqNum;
	}

	public void setHbFqNum(String hbFqNum) {
		this.hbFqNum = hbFqNum;
	}

	public String getHbFqSellerPercent() {
		return hbFqSellerPercent;
	}

	public void setHbFqSellerPercent(String hbFqSellerPercent) {
		this.hbFqSellerPercent = hbFqSellerPercent;
	}
}
