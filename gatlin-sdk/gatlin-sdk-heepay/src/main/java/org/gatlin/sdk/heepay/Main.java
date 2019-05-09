package org.gatlin.sdk.heepay;

import org.gatlin.sdk.heepay.request.QueryBankCardRequest;
import org.gatlin.sdk.heepay.response.QueryBankCardResponse;
import org.gatlin.util.serial.GsonSerializer;

public class Main {

	public static void main(String[] args) {
		QueryBankCardRequest.Builder builder = new QueryBankCardRequest.Builder();
		builder.bank_card_no("6212261202025453326");
		QueryBankCardRequest request = builder.build();
		QueryBankCardResponse response = request.sync_();
		System.out.println(new String(GsonSerializer.INSTANCE.serial(response)));
	}
}
