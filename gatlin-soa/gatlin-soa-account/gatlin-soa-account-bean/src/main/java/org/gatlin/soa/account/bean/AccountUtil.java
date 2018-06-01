package org.gatlin.soa.account.bean;

import java.math.BigDecimal;

import org.gatlin.soa.account.bean.entity.Recharge;
import org.gatlin.soa.account.bean.enums.RechargeState;
import org.gatlin.soa.bean.User;
import org.gatlin.soa.bean.enums.PlatType;
import org.gatlin.soa.bean.enums.TargetType;
import org.gatlin.soa.bean.param.SoaParam;
import org.gatlin.util.DateUtil;
import org.gatlin.util.IDWorker;

public class AccountUtil {

	public static final Recharge newRecharge(SoaParam param, PlatType plat, int goodsType, Object goodsId, BigDecimal amount, int timeout) {
		Recharge recharge = new Recharge();
		recharge.setId(IDWorker.INSTANCE.nextSid());
		User user = param.getUser();
		recharge.setOs(user.getOs());
		recharge.setPlat(plat);
		recharge.setIp(param.meta().getIp());
		recharge.setState(RechargeState.INIT);
		recharge.setGoodsType(goodsType);
		recharge.setGoodsId(goodsId.toString());
		recharge.setRechargee(param.getUser().getId());
		recharge.setRechargeeType(TargetType.USER);
		recharge.setRecharger(param.getUser().getId());
		recharge.setRechargerType(TargetType.USER);
		recharge.setOperator(param.getUser().getId());
		recharge.setFee(BigDecimal.ZERO);
		recharge.setAmount(amount);
		timeout = Math.max(0, timeout);
		int time = DateUtil.current();
		recharge.setExpiry(time + timeout * 60);
		recharge.setCreated(time);
		recharge.setUpdated(time);
		return recharge;
	}
}
