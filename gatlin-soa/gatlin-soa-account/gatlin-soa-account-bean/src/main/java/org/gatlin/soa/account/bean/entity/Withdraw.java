package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.soa.account.bean.enums.WithdrawState;
import org.gatlin.soa.bean.enums.PlatType;
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
	private long uid;
	private String ip;
	private PlatType plat;
	private BigDecimal fee;
	private BigDecimal amount;
	private WithdrawState state;
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

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
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
