package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class UserRecharge implements Identifiable<String> {

	private static final long serialVersionUID = -1986403952622140997L;

	@Id
	private String id;
	private int os;
	private int plat;
	private int state;
	private String ip;
	private int goodsType;
	private String goodsId;
	private long rechargee;
	private long recharger;
	private BigDecimal fee;
	private BigDecimal amount;
	private int expiry;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public long getRechargee() {
		return rechargee;
	}

	public void setRechargee(long rechargee) {
		this.rechargee = rechargee;
	}

	public long getRecharger() {
		return recharger;
	}

	public void setRecharger(long recharger) {
		this.recharger = recharger;
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
	
	public int getExpiry() {
		return expiry;
	}
	
	public void setExpiry(int expiry) {
		this.expiry = expiry;
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
