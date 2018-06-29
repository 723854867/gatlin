package org.gatlin.sdk.amazon.response.order;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.gatlin.sdk.amazon.response.AmazonResponse;
import org.gatlin.sdk.amazon.response.ResponseMetaData;

@XmlRootElement(name = "CreateFulfillmentOrderResponse")
public class CreateFulfillmentOrderResponse extends AmazonResponse {

	private static final long serialVersionUID = 7285246624164663444L;

	private ResponseMetaData metaData;
	
	public ResponseMetaData getMetaData() {
		return metaData;
	}
	
	@XmlElement(name = "ResponseMetadata")
	public void setMetaData(ResponseMetaData metaData) {
		this.metaData = metaData;
	}
}
