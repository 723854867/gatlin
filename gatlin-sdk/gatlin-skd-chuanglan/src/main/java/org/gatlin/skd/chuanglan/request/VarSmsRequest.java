package org.gatlin.skd.chuanglan.request;

import java.util.List;

import org.gatlin.skd.chuanglan.ChuangLanConfig;
import org.gatlin.skd.chuanglan.bean.VarMsg;
import org.gatlin.skd.chuanglan.response.VarSmsResponse;

/**
 * 变量短信
 * <pre>
 * 注：客户发送短信的账号不是253平台登录账号，是短信接口API账号 。
 * msg字段最多支持20个变量。params字段最多不超过1000个参数组。格式不符的参数，系统自动会过滤。
 * 变量短信第一个变量默认为手机号码，后面的变量依次匹配短信内容中{$var}所代表的变量，且变量符只能为{$var}，变量之间英文逗号隔开，变量参数组之间用英文分号隔开 。
 * 
 * 例子：
 * msg = 【253】尊敬的{$var},你好,您的密码是：{$var},{$var}分钟内有效
 * params = 15800000000,先生,1234,3;13800000000,女士,5678,5
 * </pre>
 * 
 * @author lynn
 */
public class VarSmsRequest extends ChuangLanRequest<VarSmsResponse, VarSmsRequest> {

	private VarSmsRequest(Builder builder) {
		super(ChuangLanConfig.pathVarSms());
		this.body = builder;
	}

	public static class Builder extends ChuangLanRequest.Builder {

		private static final long serialVersionUID = -5668040258558207532L;

		// 短信内容。长度不能超过536个字符
		protected String msg;
		// 该条短信在客户业务系统内的ID，客户自定义（选填参数）
		protected String uid;
		// 是否需要状态报告（默认为false）（选填参数）
		protected String report;
		// 用户自定义扩展码，纯数字，建议1-3位（选填参数）
		protected String extend;
		// 手机号码和变量参数，多组参数使用英文分号;区分    
		protected String params;
		// 定时发送短信时间。格式为yyyyMMddHHmm，值小于或等于当前时间则立即发送，不填则默认为立即发送（选填参数）
		protected String sendtime;
		
		public Builder msg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}
		
		public Builder report(boolean report) {
			this.report = report ? "true" : "false";
			return this;
		}
		
		public Builder params(List<VarMsg> msgs) {
			StringBuilder builder = new StringBuilder();
			for (VarMsg msg : msgs) {
				builder.append(msg.getPhone()).append(",");
				for (String var : msg.getVars())
					builder.append(var).append(",");
				builder.deleteCharAt(builder.length() - 1);
				builder.append(";");
			}
			builder.deleteCharAt(builder.length() - 1);
			this.params = builder.toString();
			return this;
		}
		
		public Builder extend(String extend) {
			this.extend = extend;
			return this;
		}
		
		public Builder sendtime(String sendtime) {
			this.sendtime = sendtime;
			return this;
		}
		
		public VarSmsRequest build() {
			return new VarSmsRequest(this);
		}
	}
}
