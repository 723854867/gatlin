package org.gatlin.sdk.sinapay.response;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.gatlin.sdk.sinapay.bean.enums.ProfitField;
import org.gatlin.sdk.sinapay.bean.model.ProfitTips;
import org.gatlin.util.reflect.BeanUtil;

import com.google.gson.annotations.SerializedName;

public class QueryBalanceResponse extends SinapayResponse {

	private static final long serialVersionUID = 4517238932561793337L;
	
	private static ProfitField[] fields = ProfitField.values();
	
	static {
		Arrays.sort(fields, (o1, o2) -> o1.priority() - o2.priority());
	}

	// 存钱罐收益：昨日收益^最近一月收益^总收益。
	private String bonus;
	// 余额
	private BigDecimal balance;
	// 可用余额
	@SerializedName("available_balance")
	private BigDecimal availableBalance;

	public String getBonus() {
		return bonus;
	}

	public void setBonus(String bonus) {
		this.bonus = bonus;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}

	public ProfitTips profit() {
		if (null == bonus)
			return new ProfitTips();
		int idx = 0;
		StringTokenizer tokenizer = new StringTokenizer(bonus, "^");
		Map<String, String> params = new HashMap<String, String>();
		while (tokenizer.hasMoreElements()) {
			String value = tokenizer.nextToken();
			ProfitField field = fields[idx++];
			params.put(field.mark(), value);
		}
		ProfitTips bean = new ProfitTips();
		return BeanUtil.mapToBean(params, bean);
	}
}
