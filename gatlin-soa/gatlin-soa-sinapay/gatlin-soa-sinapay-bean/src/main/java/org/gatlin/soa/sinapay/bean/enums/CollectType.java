package org.gatlin.soa.sinapay.bean.enums;

public enum CollectType {

	// 充值待收
	RECHARGE {
		@Override
		public String describe() {
			return "充值待收";
		}
	},
	// 提现失败待收
	WITHDRAW_FAILED {
		@Override
		public String describe() {
			return "提现失败待收";
		}
	},
	// 提现超时待收
	WITHDRAW_TIMEOUT {
		@Override
		public String describe() {
			return "提现超时待收";
		}
	};
	
	public abstract String describe(); 
}
