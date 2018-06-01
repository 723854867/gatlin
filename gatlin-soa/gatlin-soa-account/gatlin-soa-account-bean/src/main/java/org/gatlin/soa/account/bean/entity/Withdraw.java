package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.account.bean.enums.WithdrawState;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.bean.enums.OS;

/**
 * amount 不包括 fee 比如 amount=10，fee=2，则扣除用户12块钱，到账10块钱
 * 
 * @author lynn
 */
public class Withdraw implements Identifiable<String> {

	private static final long serialVersionUID = 3301769782222966633L;

	@Id
	private String id;
	private OS os;
	private String ip;
	private PlatType plat;
	private long operator;
	private BigDecimal fee;
	private long withdrawer;
	private long withdrawee;
	private BigDecimal amount;
	private WithdrawState state;
	private AccountType accountType;
	private TargetType withdraweeType;
	private TargetType withdrawerType;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public PlatType getPlat() {
		return plat;
	}

	public void setPlat(PlatType plat) {
		this.plat = plat;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public long getWithdrawer() {
		return withdrawer;
	}

	public void setWithdrawer(long withdrawer) {
		this.withdrawer = withdrawer;
	}

	public long getWithdrawee() {
		return withdrawee;
	}

	public void setWithdrawee(long withdrawee) {
		this.withdrawee = withdrawee;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public WithdrawState getState() {
		return state;
	}

	public void setState(WithdrawState state) {
		this.state = state;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public TargetType getWithdraweeType() {
		return withdraweeType;
	}

	public void setWithdraweeType(TargetType withdraweeType) {
		this.withdraweeType = withdraweeType;
	}

	public TargetType getWithdrawerType() {
		return withdrawerType;
	}

	public void setWithdrawerType(TargetType withdrawerType) {
		this.withdrawerType = withdrawerType;
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
