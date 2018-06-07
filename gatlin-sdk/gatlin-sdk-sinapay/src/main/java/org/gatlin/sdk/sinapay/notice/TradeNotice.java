package org.gatlin.sdk.sinapay.notice;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.sdk.sinapay.bean.enums.TradeState;

public class TradeNotice extends SinaNotice {

	private static final long serialVersionUID = 7287692695576876636L;

	@NotEmpty
	private String outer_trade_no;
	@NotEmpty
	private String inner_trade_no;
	@NotNull
	private TradeState trade_status;
	private String trade_amount;
	private String gmt_create;
	private String gmt_payment;
	private String gmt_close;
	private String pay_method;
	private String auth_fihish_amount;

	public String getOuter_trade_no() {
		return outer_trade_no;
	}

	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}

	public String getInner_trade_no() {
		return inner_trade_no;
	}

	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}

	public TradeState getTrade_status() {
		return trade_status;
	}
	
	public void setTrade_status(TradeState trade_status) {
		this.trade_status = trade_status;
	}

	public String getTrade_amount() {
		return trade_amount;
	}

	public void setTrade_amount(String trade_amount) {
		this.trade_amount = trade_amount;
	}

	public String getGmt_create() {
		return gmt_create;
	}

	public void setGmt_create(String gmt_create) {
		this.gmt_create = gmt_create;
	}

	public String getGmt_payment() {
		return gmt_payment;
	}

	public void setGmt_payment(String gmt_payment) {
		this.gmt_payment = gmt_payment;
	}

	public String getGmt_close() {
		return gmt_close;
	}

	public void setGmt_close(String gmt_close) {
		this.gmt_close = gmt_close;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getAuth_fihish_amount() {
		return auth_fihish_amount;
	}

	public void setAuth_fihish_amount(String auth_fihish_amount) {
		this.auth_fihish_amount = auth_fihish_amount;
	}
}
