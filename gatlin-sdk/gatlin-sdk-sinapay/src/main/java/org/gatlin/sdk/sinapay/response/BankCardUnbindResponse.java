package org.gatlin.sdk.sinapay.response;

public class BankCardUnbindResponse extends SinapayResponse {

	private static final long serialVersionUID = -7130834236852415302L;

	private String ticket;
	
	public String getTicket() {
		return ticket;
	}
	
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
