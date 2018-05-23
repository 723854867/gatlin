package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

/**
 * amount 不包括 fee
 * 比如 amount=10，fee=2，则扣除用户12块钱，到账10块钱
 * 
 * @author lynn
 */
public class Withdraw implements Identifiable<String> {

	private static final long serialVersionUID = 3301769782222966633L;

	@Id
	private String id;
	private int os;
	private int plat;
	private int state;
	private String ip;
	private long withdrawee;
	private long withdrawer;
	private int withdraweeType;
	private int withdrawerType;
	private int withdrawerAccountType;
	private long operator;
	private BigDecimal amount;
	private BigDecimal fee;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public int getPlat() {
		return plat;
	}

	public void setPlat(int plat) {
		this.plat = plat;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getWithdrawee() {
		return withdrawee;
	}

	public void setWithdrawee(long withdrawee) {
		this.withdrawee = withdrawee;
	}
	
	public long getWithdrawer() {
		return withdrawer;
	}
	
	public void setWithdrawer(long withdrawer) {
		this.withdrawer = withdrawer;
	}

	public int getWithdraweeType() {
		return withdraweeType;
	}

	public void setWithdraweeType(int withdraweeType) {
		this.withdraweeType = withdraweeType;
	}
	
	public int getWithdrawerType() {
		return withdrawerType;
	}
	
	public void setWithdrawerType(int withdrawerType) {
		this.withdrawerType = withdrawerType;
	}
	
	public int getWithdrawerAccountType() {
		return withdrawerAccountType;
	}
	
	public void setWithdrawerAccountType(int withdrawerAccountType) {
		this.withdrawerAccountType = withdrawerAccountType;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	@Override
	public String key() {
		return this.id;
	}
}
