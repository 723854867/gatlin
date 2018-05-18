package org.gatlin.soa.sinapay.bean.entity;

import java.math.BigDecimal;

import javax.persistence.Id;

import org.gatlin.util.bean.Identifiable;

public class SinaRecharge implements Identifiable<String> {

	private static final long serialVersionUID = 8366008745502022926L;
	
	@Id
	private String id;
	private int goodsType;
	private String goodsId;
	private long rechargee;
	private long recharger;
	private BigDecimal fee;
	private BigDecimal amount;
	private int expiry;
	private int created;
	private int updated;

	@Override
	public String key() {
		return null;
	}
}
