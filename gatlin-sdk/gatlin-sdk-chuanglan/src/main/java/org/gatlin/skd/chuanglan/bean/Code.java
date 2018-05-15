package org.gatlin.skd.chuanglan.bean;

public enum Code {

	// 提交成功
	SUCCESS("0"),
	// 	无此用户
	USER_NOT_EXIST("101"),
	// 密码错误
	PASSWORD_ERR("102"),
	// 提交过快（提交速度超过流速限制）
	SUBMIT_FREQUENCY("103"),
	// 系统忙（因平台侧原因，暂时无法处理提交的短信）
	SERVER_BUSY("104"),
	// 敏感短信（短信内容包含敏感词）
	CONTENT_FORBID("105"),
	// 消息长度错（>536或<=0）
	MSG_TOO_LONG("106"),
	// 	包含错误的手机号码
	MOBILE_ERR("107"),
	// 手机号码个数错（群发>1000或<=0）
	MOBILE_NUM_ERR("108"),
	// 无发送额度（该用户可用短信数已使用完）
	BALANCE_LACK("109"),
	// 不在发送时间内
	TIME_ERR("110"),
	// 扩展码格式错（非数字或者长度不对）
	EXTEND_ERR("113"),
	// 可用参数组个数错误（小于最小设定值或者大于1000）;变量参数组大于20个
	PARAMS_ERR("114"),
	// 签名不合法或未带签名（用户必须带签名的前提下）
	SIGN_ERR("116"),
	// IP地址认证错,请求调用的IP地址不是系统登记的IP地址
	IP_ERR("117"),
	// 用户没有相应的发送权限（账号被禁止发送）
	USER_PRIVILEGE_LIMIT("118"),
	// 用户已过期
	USER_EXPIRE("119"),
	// 违反防盗用策略(日发送限制)
	AMOUNT_LIMIT("120"),
	// 发送类型错误
	TYPE_ERR("123"),
	// 白模板匹配错误
	TEMPLATE_ERR("124"),
	// 匹配驳回模板，提交失败
	TEMPLATE_SUBMIT_REBACK("125"),
	// 定时发送时间格式错误
	TIME_FORMAT_ERR("127"),
	// 内容编码失败
	CONTENT_ENCODE_ERR("128"),
	// JSON格式错误
	JSON_ERR("129"),
	// 请求参数错误（缺少必填参数）
	REQUEST_PARAM_ERR("130");
	
	private String mark;
	
	private Code(String mark) {
		this.mark = mark;
	}
	
	public String mark() {
		return mark;
	}
	
	public static final Code match(String code) { 
		for (Code temp : Code.values()) {
			if (temp.mark.equals(code))
				return temp;
		}
		return null;
	}
}
