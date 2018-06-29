package org.gatlin.sdk.amazon.request;

import org.gatlin.sdk.amazon.response.GetServiceStatusResponse;

public class GetServiceStatusRequest extends AmazonRequest<GetServiceStatusResponse, GetServiceStatusRequest> {

	public GetServiceStatusRequest() {
		super("GetServiceStatus", "2011-10-01", "Products/2011-10-01");
	}
}
