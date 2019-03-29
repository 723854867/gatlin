package org.gatlin.sdk.heepay;

public interface HeepayConfig {

	//小额正式交易地址
//	String SMALL_AMOUNT_URL = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";
	String SMALL_AMOUNT_URL = "http://211.103.157.45/payheepay/API/PayTransit/PayTransferWithSmallAll.aspx";
	String SMALL_AMOUNT_HOST = "211.103.157.45";
	int SMALL_AMOUNT_PORT = 80;
	String SMALL_AMOUNT_PATH = "/payheepay/API/PayTransit/PayTransferWithSmallAll.aspx";
	//大额正式交易地址
	String BIG_AMOUNT_URL = "https://Pay.heepay.com/API/PayTransit/PayTransferWithSmallAll.aspx";
	//商户id
	String agent_id = "2119474";
	//商户付款理由
	String remit_reason = "上游厂商结算款";
	//批量付款回调地址
	String notify_url = "";
	//商户自定义原样返回，最大长度50个字符
	String ext_param1 = "remit";
	//3DES 加密的KEY
	String DES_KEY = "B151BB6B4F9C463594EA1DFB";
	//商户密钥
	String KEY = "D512553B68694D0D9806484E";
}
