package org.gatlin.sdk.amazon.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.gatlin.sdk.amazon.bean.AmazonException;

@XmlRootElement(name = "ErrorResponse")
public class ErrorResponse extends AmazonResponse {

	private static final long serialVersionUID = 7996532649688393523L;
	
	private Error error;
	private String requestId;
	
	public Error getError() {
		return error;
	}
	
	@XmlElement(name = "Error")
	public void setError(Error error) {
		this.error = error;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	@XmlElement(name = "RequestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Override
	public String code() {
		return error.code;
	}
	
	@Override
	public String desc() {
		return error.message;
	}
	
	@Override
	public void verify() {
		throw new AmazonException(code(), desc());
	}

	public static final class Error implements Serializable {

		private static final long serialVersionUID = -421336418507826408L;

		private String type;
		private String code;
		private String message;

		public String getType() {
			return type;
		}

		@XmlElement(name = "Type")
		public void setType(String type) {
			this.type = type;
		}

		public String getCode() {
			return code;
		}

		@XmlElement(name = "Code")
		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		@XmlElement(name = "Message")
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
