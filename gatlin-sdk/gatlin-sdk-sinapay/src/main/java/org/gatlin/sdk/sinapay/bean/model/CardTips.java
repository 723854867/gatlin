package org.gatlin.sdk.sinapay.bean.model;

import java.io.Serializable;

public class CardTips implements Serializable {

	private static final long serialVersionUID = 3742100426429961185L;

	private String cardId;
	private String bankCode;
	private String bankNo;
	private String accountName;
	private String cardType;
	private String cardAttribute;
	private boolean verify;
	private String time;
	// 是否是安全卡
	private boolean security;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardAttribute() {
		return cardAttribute;
	}

	public void setCardAttribute(String cardAttribute) {
		this.cardAttribute = cardAttribute;
	}

	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}
}
