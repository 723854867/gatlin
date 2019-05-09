package org.gatlin.sdk.heepay.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class QueryBankCardResponse extends BatchPayResponse {
	
	private static final long serialVersionUID = -4454468388907417122L;
	private String agent_id;
	private String bank_card_no;
	private String bank_name;
	private String bank_type;
	private String bank_card_type;

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getBank_card_no() {
		return bank_card_no;
	}

	public void setBank_card_no(String bank_card_no) {
		this.bank_card_no = bank_card_no;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public String getBank_card_type() {
		return bank_card_type;
	}

	public void setBank_card_type(String bank_card_type) {
		this.bank_card_type = bank_card_type;
	}
}
