package org.gatlin.soa.sinapay.bean.param;

import org.gatlin.soa.bean.param.SoaSidParam;

public class CompanyBankCardModifyParam extends SoaSidParam {

	private static final long serialVersionUID = 5105065684399245507L;

	private String cardId;
	
	public String getCardId() {
		return cardId;
	}
	
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
}
