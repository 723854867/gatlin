package org.gatlin.sdk.heepay.notice;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.core.bean.model.message.Notice;

public class BatchPayNotice extends Notice {

	private static final long serialVersionUID = 3089595580576246345L;

	public static final String RESPONSE_OK = "ok";

	// 返回码值0000 表示查询成功
	@NotEmpty
	private String ret_code;
	// 返回码信息提示
	@NotEmpty
	private String ret_msg;
	// 商户编号 如1234567
	@NotEmpty
	private String agent_id;
	// 汇付宝交易号(订单号)
	private String hy_bill_no;
	@NotEmpty
	private String status;
	// 商户系统内部的订单号
	@NotEmpty
	private String batch_no;
	// 成功付款金额
	@NotEmpty
	private String batch_amt;
	// 成功付款数量
	@NotEmpty
	private String batch_num;
	// 付款明细，单笔数据集里面按照“商户流水号^收款人帐号^收款人姓名^付款金额^付款状态”来组织数据，每条整数据间用“竖线”符号分隔，付款状态S表示付款成功，状态F代表失败
	@NotEmpty
	private String detail_data;
	// 商家数据包，原样返回
	@NotEmpty
	private String ext_param1;
	@NotEmpty
	private String sign;

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getHy_bill_no() {
		return hy_bill_no;
	}

	public void setHy_bill_no(String hy_bill_no) {
		this.hy_bill_no = hy_bill_no;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatch_amt() {
		return batch_amt;
	}

	public void setBatch_amt(String batch_amt) {
		this.batch_amt = batch_amt;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public String getDetail_data() {
		return detail_data;
	}

	public void setDetail_data(String detail_data) {
		this.detail_data = detail_data;
	}

	public String getExt_param1() {
		return ext_param1;
	}

	public void setExt_param1(String ext_param1) {
		this.ext_param1 = ext_param1;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getResponseOk() {
		return RESPONSE_OK;
	}
}
