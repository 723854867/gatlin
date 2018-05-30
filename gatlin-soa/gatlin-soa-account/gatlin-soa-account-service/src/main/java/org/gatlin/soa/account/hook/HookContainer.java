package org.gatlin.soa.account.hook;

import java.util.Map;

import org.gatlin.core.bean.exceptions.CodeException;
import org.gatlin.core.util.SpringContextUtil;

public class HookContainer {

	public static final IRechargeNoticeHook rechageNoticeHook() {
		Map<String, IRechargeNoticeHook> hooks = SpringContextUtil.getBeansOfType(IRechargeNoticeHook.class);
		if (hooks.size() == 1)
			return hooks.values().iterator().next();
		if (hooks.size() == 2) {
			for (IRechargeNoticeHook hook : hooks.values()) {
				Class<?> clazz = hook.getClass();
				if (clazz == DefaultRechargeNoticeHook.class)
					continue;
				return hook;
			}
		}
		throw new CodeException();
	}
}
