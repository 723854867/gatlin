package org.gatlin.soa.bean.param;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.gatlin.core.CoreCode;
import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.Assert;
import org.gatlin.soa.bean.enums.AccountType;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;

public class WithdrawParam extends SoaParam {

	private static final long serialVersionUID = 4460675374479082610L;

	@Null
	private PlatType plat;
	private BigDecimal fee;
	private Long withdrawee;
	private Integer companyId;
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal amount;
	@NotNull
	private AccountType accountType;
	@NotNull
	private TargetType withdraweeType;
	
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
	
	public Long getWithdrawee() {
		return withdrawee;
	}
	
	public void setWithdrawee(Long withdrawee) {
		this.withdrawee = withdrawee;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
	
	@Override
	public void verify() {
		super.verify();
		switch (withdraweeType) {
		case USER:
			this.withdrawee = getUser().getId();
			break;
		case COMPANY:
			Assert.notNull(CoreCode.PARAM_ERR, withdrawee);
			break;
		default:
			throw new CodeException(CoreCode.PARAM_ERR);
		}
		if (null == fee)
			this.fee = BigDecimal.ZERO;
	}
}
