package org.gatlin.sdk.heepay.bean.enums;

public enum Code {

	MD5_FAILE("10000","MD5加密异常"),
	PAY_SUCCESS("0000","执行成功"),
	E100("E100","商户未授权或没有开通API接口"),
	E101("E101","签名验证错误"),
	E102("E102","数据解密错误"),
	E103("E103","时间戳过期"),
	E104("E104","输入参数验证错误"),
	E105("E105","银行返回的具体错误描述"),
	E106("E106","风险控制错误"),
	E107("E107","授权过期"),
	E108("E108","限制错误"),
	E109("E109","商户账户资金不足"),
	E110("E110","单据号已经存在"),
	U999("U999","未知错误");
	

	private String mark;
	private String desc;
	
	private Code(String mark,String desc) {
		this.mark = mark;
		this.desc = desc;
	}
	
	public String mark() {
		return mark;
	}
	
	public String desc() {
		return desc;
	}
	
	public static final Code match(String code) {
		for (Code temp : Code.values()) {
			if (temp.mark.equals(code))
				return temp;
		}
		return null;
	}
}
