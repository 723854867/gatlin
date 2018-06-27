package org.gatlin.sdk.amazon.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.gatlin.sdk.amazon.bean.enums.ServiceStatus;

@XmlRootElement(name = "GetServiceStatusResponse")
public class GetServiceStatusResponse extends AmazonResponse {

	private static final long serialVersionUID = -7172020577150274444L;

	private Result result;
	private ResponseMetaData metaData;
	
	public Result getResult() {
		return result;
	}
	
	@XmlElement(name = "GetServiceStatusResult")
	public void setResult(Result result) {
		this.result = result;
	}
	
	public ResponseMetaData getMetaData() {
		return metaData;
	}
	
	@XmlElement(name = "ResponseMetadata")
	public void setMetaData(ResponseMetaData metaData) {
		this.metaData = metaData;
	}
	
	public static class Result implements Serializable {
		
		private static final long serialVersionUID = -1763512812639943197L;
		
		private String timestamp;
		private ServiceStatus status;
		
		public String getTimestamp() {
			return timestamp;
		}
		
		@XmlElement(name = "Timestamp")
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
		public ServiceStatus getStatus() {
			return status;
		}
		
		@XmlElement(name = "Status")
		public void setStatus(ServiceStatus status) {
			this.status = status;
		}
	}
}
