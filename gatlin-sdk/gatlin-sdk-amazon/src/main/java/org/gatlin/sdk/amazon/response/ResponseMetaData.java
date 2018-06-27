package org.gatlin.sdk.amazon.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class ResponseMetaData implements Serializable {

	private static final long serialVersionUID = 1446110969823481054L;

	private String requestId;
	
	public String getRequestId() {
		return requestId;
	}
	
	@XmlElement(name = "RequestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
