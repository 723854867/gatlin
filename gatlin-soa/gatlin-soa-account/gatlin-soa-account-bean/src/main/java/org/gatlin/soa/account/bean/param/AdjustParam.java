package org.gatlin.soa.account.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gatlin.soa.account.bean.enums.AccountField;
import org.gatlin.soa.account.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;

public class AdjustParam extends SoaParam {

	private static final long serialVersionUID = 9448845910339941L;

	@Min(0)
	private long owner;
	@NotEmpty
	private String memo;
	@NotNull
	private AccountType type;
	@NotNull
	private BigDecimal amount;
	@NotNull
	private AccountField Field;
	@NotNull
	private TargetType ownerType;

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public AccountField getField() {
		return Field;
	}
	
	public void setField(AccountField field) {
		Field = field;
	}

	public TargetType getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(TargetType ownerType) {
		this.ownerType = ownerType;
	}
}
