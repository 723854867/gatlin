package org.gatlin.soa.account.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.util.bean.Identifiable;
import org.gatlin.util.bean.enums.OS;

/**
 * amount包括fee 比如amount=10，fee=2则最终到账只有8，2进入公司账户
 * 
 * @author lynn
 */
public class Recharge implements Identifiable<String> {

	private static final long serialVersionUID = -1986403952622140997L;

	@Id
	private String id;
	private OS os;
	private String ip;
	private int expiry;
	private PlatType plat;
	private int goodsType;
	private long operator;
	private String goodsId;
	private long rechargee;
	private long recharger;
	private BigDecimal fee;
	private BigDecimal amount;
	private RechargeState state;
	private TargetType rechargeeType;
	private TargetType rechargerType;
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

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public long getOperator() {
		return operator;
	}

	public void setOperator(long operator) {
		this.operator = operator;
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

	public RechargeState getState() {
		return state;
	}

	public void setState(RechargeState state) {
		this.state = state;
	}

	public TargetType getRechargeeType() {
		return rechargeeType;
	}

	public void setRechargeeType(TargetType rechargeeType) {
		this.rechargeeType = rechargeeType;
	}

	public TargetType getRechargerType() {
		return rechargerType;
	}

	public void setRechargerType(TargetType rechargerType) {
		this.rechargerType = rechargerType;
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
