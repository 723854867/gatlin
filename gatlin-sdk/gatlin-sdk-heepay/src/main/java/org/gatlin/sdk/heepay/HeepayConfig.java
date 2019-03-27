package org.gatlin.sdk.heepay;

public interface HeepayConfig {

	//小额正式交易地址
	String SMALL_AMOUNT_URL = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";
	//大额正式交易地址
	String BIG_AMOUNT_URL = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";
	//商户id
	String agent_id = "1664502";
	//批量付款回调地址
	String notify_url = "";
	//商户自定义原样返回，最大长度50个字符
	String ext_param1 = "商户自定义原样返回";
	//3DES 加密的KEY
	String DES_KEY = "4B05A95416DB4184ACEE4313";
	//商户密钥
	String KEY = "4B05A95416DB4184ACEE4313";
}
