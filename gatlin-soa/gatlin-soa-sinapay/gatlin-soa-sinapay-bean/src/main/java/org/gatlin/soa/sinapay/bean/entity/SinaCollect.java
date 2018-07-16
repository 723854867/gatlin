package org.gatlin.soa.sinapay.bean.entity;

import javax.persistence.Id;

import org.gatlin.sdk.sinapay.bean.enums.TradeState;
import org.gatlin.soa.sinapay.bean.enums.CollectType;
import org.gatlin.util.bean.Identifiable;

public class SinaCollect implements Identifiable<String> {

	private static final long serialVersionUID = 3072145233275531189L;

	@Id
	private String id;
	private String tid;
	private String relativeNo;
	private CollectType type;
	private TradeState state;
	private int created;
	private int updated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getRelativeNo() {
		return relativeNo;
	}
	
	public void setRelativeNo(String relativeNo) {
		this.relativeNo = relativeNo;
	}
	
	public CollectType getType() {
		return type;
	}

	public void setType(CollectType type) {
		this.type = type;
	}

	public TradeState getState() {
		return state;
	}

	public void setState(TradeState state) {
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
		return id;
	}
}
